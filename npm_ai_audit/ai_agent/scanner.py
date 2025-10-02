import os
import json
import subprocess

def scan_dependencies(path):
    package_json_path = os.path.join(path, "package.json")
    if not os.path.exists(package_json_path):
        raise FileNotFoundError("package.json not found in the given path.")

    with open(package_json_path) as f:
        package_data = json.load(f)

    dependencies = package_data.get("dependencies", {})
    dev_dependencies = package_data.get("devDependencies", {})

    all_deps = {**dependencies, **dev_dependencies}

    outdated_info = {}
    try:
        output = subprocess.check_output(["npm", "outdated", "--json"], cwd=path, text=True)
        outdated_info = json.loads(output)
    except subprocess.CalledProcessError as e:
        if e.output:
            outdated_info = json.loads(e.output)

    for dep in all_deps:
        if dep in outdated_info:
            all_deps[dep] = {
                "current": outdated_info[dep]["current"],
                "wanted": outdated_info[dep]["wanted"],
                "latest": outdated_info[dep]["latest"]
            }
        else:
            all_deps[dep] = {
                "current": all_deps[dep],
                "latest": all_deps[dep]
            }

    return all_deps
