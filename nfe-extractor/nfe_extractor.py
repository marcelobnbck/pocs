import os
import csv
import traceback
import argparse
from concurrent.futures import ThreadPoolExecutor, as_completed
from xml.etree import ElementTree as ET
from tqdm import tqdm
import pandas as pd

def local_name(tag):
    return tag.split('}')[-1] if '}' in tag else tag

def find_child(elem, name):
    if elem is None:
        return None
    for child in elem:
        if local_name(child.tag) == name:
            return child
    return None

def find_descendant(elem, name):
    if elem is None:
        return None
    stack = [elem]
    while stack:
        e = stack.pop()
        if local_name(e.tag) == name:
            return e
        stack.extend(list(e))
    return None

def find_descendants(elem, name):
    matches = []
    stack = [elem]
    while stack:
        e = stack.pop()
        if local_name(e.tag) == name:
            matches.append(e)
        stack.extend(list(e))
    return matches

def get_text(elem, name):
    c = find_child(elem, name)
    return c.text.strip() if c is not None and c.text else ""

def get_first_text_from_path(root, path_names):
    e = root
    for p in path_names:
        e = find_child(e, p)
        if e is None:
            return ""
    return e.text.strip() if e.text else ""

def extract_file(filepath):
    tree = ET.parse(filepath)
    root = tree.getroot()
    infNFe = find_descendant(root, 'infNFe')
    if infNFe is None:
        raise ValueError("infNFe element not found")

    nNF = get_first_text_from_path(infNFe, ['ide', 'nNF'])
    dhEmi = get_first_text_from_path(infNFe, ['ide', 'dhEmi'])
    dets = find_descendants(infNFe, 'det')

    rows = []
    for det in dets:
        prod = find_child(det, 'prod')
        if prod is None:
            continue

        row = {
            "nNF": nNF,
            "Data Emissao": dhEmi,
            "Codigo Produto": get_text(prod, 'cProd'),
            "Descricao Produto": get_text(prod, 'xProd'),
            "NCM": get_text(prod, 'NCM'),
            "CFOP": get_text(prod, 'CFOP'),
            "Quantidade": get_text(prod, 'qCom'),
            "Valor Unitario": get_text(prod, 'vUnCom'),
            "Valor Total Produtos": get_text(prod, 'vProd'),
            "vICMSSTRet": "",
            "PIS CST": "",
            "COFINS CST": "",
        }

        imposto = find_child(det, 'imposto')
        if imposto is not None:
            vICMSSTRet = find_descendant(imposto, 'vICMSSTRet')
            row["vICMSSTRet"] = vICMSSTRet.text.strip() if vICMSSTRet is not None and vICMSSTRet.text else ""

            for child in list(imposto):
                if local_name(child.tag).startswith("PIS"):
                    cst = find_descendant(child, "CST")
                    if cst is not None and cst.text:
                        row["PIS CST"] = cst.text.strip()
                        break
            for child in list(imposto):
                if local_name(child.tag).startswith("COFINS"):
                    cst = find_descendant(child, "CST")
                    if cst is not None and cst.text:
                        row["COFINS CST"] = cst.text.strip()
                        break

        rows.append(row)
    return rows

def process_files(input_dir, output_path, concurrency):
    headers = [
        "nNF", "Data Emissao", "Codigo Produto", "Descricao Produto", "NCM",
        "CFOP", "Quantidade", "Valor Unitario", "Valor Total Produtos",
        "vICMSSTRet", "PIS CST", "COFINS CST"
    ]

    xml_files = [os.path.join(input_dir, f)
                 for f in os.listdir(input_dir) if f.lower().endswith(".xml")]
    if not xml_files:
        print("No XML files found in:", input_dir)
        return

    print(f"Found {len(xml_files)} XML files.")
    csv_out = os.path.splitext(output_path)[0] + ".csv"
    err_log = os.path.splitext(output_path)[0] + "_errors.csv"

    all_rows = []
    errors = []

    with ThreadPoolExecutor(max_workers=concurrency) as executor:
        futures = {executor.submit(extract_file, f): f for f in xml_files}
        for fut in tqdm(as_completed(futures), total=len(futures), desc="Processing XMLs"):
            fpath = futures[fut]
            try:
                all_rows.extend(fut.result())
            except Exception as e:
                errors.append((os.path.basename(fpath), str(e)))
                with open(os.path.splitext(fpath)[0] + ".error.log", "w", encoding="utf-8") as ef:
                    ef.write(traceback.format_exc())

    with open(csv_out, "w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=headers)
        writer.writeheader()
        writer.writerows(all_rows)
    print(f"✅ CSV saved to: {csv_out}")

    if errors:
        with open(err_log, "w", newline="", encoding="utf-8") as ef:
            cw = csv.writer(ef)
            cw.writerow(["filename", "error"])
            cw.writerows(errors)
        print(f"⚠️  {len(errors)} errors logged in: {err_log}")

    try:
        df = pd.DataFrame(all_rows)
        df.to_excel(output_path, index=False)
        print(f"✅ XLSX saved to: {output_path}")
    except Exception as e:
        print("⚠️ Could not generate XLSX:", e)

def main():
    parser = argparse.ArgumentParser(description="Extract key fields NFe XML files into CSV/XLSX.")
    parser.add_argument("--input-dir", required=True, help="Directory containing .xml files to read")
    parser.add_argument("--output", required=True, help="Output Excel file path (e.g., result.xlsx)")
    parser.add_argument("--concurrency", type=int, default=4, help="Number of worker threads")
    args = parser.parse_args()

    os.makedirs(os.path.dirname(args.output), exist_ok=True)
    process_files(args.input_dir, args.output, args.concurrency)

if __name__ == "__main__":
    main()
