import os
import pandas as pd
from lxml import etree
from concurrent.futures import ProcessPoolExecutor, as_completed
from tqdm import tqdm
import time

# === CONFIGURAÇÕES ===
CAMINHO_PASTA = r"C:\NFe\XMLs"   # 👉 altere para a pasta onde estão os XMLs
ARQUIVO_SAIDA = "Relatorio_Produtos_ICMS_PIS_COFINS_FINAL.xlsx"
ARQUIVO_TEMP = "relatorio_temp_parcial.csv"  # arquivo parcial salvo durante o processo
NS = {"nfe": "http://www.portalfiscal.inf.br/nfe"}
MAX_WORKERS = 8  # número de núcleos do processador
TAMANHO_BLOCO = 1000  # grava a cada X XMLs processados
# =====================

def extrair_dados_xml(xml_path):
    """Extrai dados de um único XML."""
    try:
        tree = etree.parse(xml_path)
        dados = []
        for det in tree.xpath("//nfe:det", namespaces=NS):
            get = lambda path: det.xpath(path + "/text()", namespaces=NS)[0] if det.xpath(path + "/text()", namespaces=NS) else ""
            dados.append({
                "Arquivo XML": os.path.basename(xml_path),
                "Código Produto": get("nfe:prod/nfe:cProd"),
                "Descrição Produto": get("nfe:prod/nfe:xProd"),
                "CFOP": get("nfe:prod/nfe:CFOP"),
                "Quantidade": get("nfe:prod/nfe:qCom"),
                "Valor Unitário": get("nfe:prod/nfe:vUnCom"),
                "Valor Total Produto": get("nfe:prod/nfe:vProd"),
                "vICMSSTRet": get(".//nfe:vICMSSTRet"),
                "CST PIS": get(".//nfe:PIS//nfe:CST"),
                "CST COFINS": get(".//nfe:COFINS//nfe:CST"),
                "nNF": get("nfe:ide/nfe:nNF"),
                "NCM": get("nfe:prod/nfe:NCM"),
                "dhEmi": get("nfe:ide/nfe:dhEmi")
            })
        return dados
    except Exception as e:
        return [{"Arquivo XML": os.path.basename(xml_path), "Erro": str(e)}]


def listar_xmls(caminho_base):
    """Lista todos os XMLs em uma pasta e subpastas."""
    xmls = []
    for raiz, _, files in os.walk(caminho_base):
        for f in files:
            if f.lower().endswith(".xml"):
                xmls.append(os.path.join(raiz, f))
    return xmls

def salvar_parcial(dados):
    """Grava dados parciais no CSV."""
    df = pd.DataFrame(dados)
    modo = "a" if os.path.exists(ARQUIVO_TEMP) else "w"
    cabecalho = not os.path.exists(ARQUIVO_TEMP)
    df.to_csv(ARQUIVO_TEMP, mode=modo, header=cabecalho, index=False, sep=";", encoding="utf-8-sig")

def processar_todos_xmls():
    inicio = time.time()
    xmls = listar_xmls(CAMINHO_PASTA)
    print(f"🔍 Total de XMLs encontrados: {len(xmls)}")

    resultados_temp = []
    erros = []

    with ProcessPoolExecutor(max_workers=MAX_WORKERS) as executor:
        futuros = {executor.submit(extrair_dados_xml, xml): xml for xml in xmls}
        for i, futuro in enumerate(tqdm(as_completed(futuros), total=len(xmls), desc="Processando XMLs", ncols=100)):
            resultado = futuro.result()
            if resultado and "Erro" in resultado[0]:
                erros.append(resultado[0])
            else:
                resultados_temp.extend(resultado)

            # Grava parcial a cada X arquivos processados
            if (i + 1) % TAMANHO_BLOCO == 0:
                salvar_parcial(resultados_temp)
                resultados_temp = []

    # Salva o que restou
    if resultados_temp:
        salvar_parcial(resultados_temp)

    # Consolida os parciais em Excel
    df_final = pd.read_csv(ARQUIVO_TEMP, sep=";", encoding="utf-8-sig")
    df_final.to_excel(ARQUIVO_SAIDA, index=False)

    fim = time.time()
    print(f"\n✅ Relatório final gerado: {ARQUIVO_SAIDA}")
    print(f"⏱️ Tempo total: {round((fim - inicio)/60, 2)} minutos")

    if erros:
        pd.DataFrame(erros).to_csv("Erros_Extracao.csv", index=False)
        print(f"⚠️ {len(erros)} XMLs apresentaram erro — veja 'Erros_Extracao.csv'.")

if __name__ == "__main__":
    processar_todos_xmls()
