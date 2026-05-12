import json, os

files = [f for f in os.listdir(".") if f.startswith("figma") and f.endswith(".json")]
targets = ["Tone Strictness Settings", "Setting / Tone & Strictness", "Settings", "SettingsScreen"]

def print_node(node, depth=0):
    indent = "  " * depth
    name = node.get("name", "")
    type_ = node.get("type", "")
    chars = node.get("characters", "")
    
    info = f"{indent}- [{type_}] {name}"
    if chars:
        info += f" : \"{chars}\""
        
    style = node.get("style", {})
    if style:
        fontSize = style.get("fontSize", "")
        fontWeight = style.get("fontWeight", "")
        if fontSize or fontWeight:
            info += f" (Font: {fontWeight} {fontSize}px)"
            
    print(info)
    
    for child in node.get("children", []):
        print_node(child, depth + 1)

for f in files:
    try:
        with open(f, "r") as file:
            data = json.load(file)
            
            def search_node(node):
                if node.get("name", "") in targets:
                    print(f"\n--- Found {node.get('name')} in {f} ---")
                    print_node(node)
                for child in node.get("children", []):
                    search_node(child)

            if "document" in data:
                search_node(data["document"])
            elif "nodes" in data:
                for node_id, node_info in data["nodes"].items():
                    if "document" in node_info:
                        search_node(node_info["document"])
            elif isinstance(data, list):
                for item in data:
                    search_node(item)
    except Exception as e:
        pass
