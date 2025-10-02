def fake_ai_response(lib, current, latest):
    return {
        "migration_guide": f"https://github.com/{lib}/{lib}/releases",  # Simulated
        "code_fix": f"Replace incompatible APIs from v{current} to v{latest}.",
        "risk": "Low" if current.split('.')[0] == latest.split('.')[0] else "High"
    }

def analyze_dependencies(dependencies):
    results = {}
    for lib, info in dependencies.items():
        if info["current"] != info["latest"]:
            ai_output = fake_ai_response(lib, info["current"], info["latest"])
            results[lib] = {
                **info,
                **ai_output
            }
    return results
