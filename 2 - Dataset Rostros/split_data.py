import splitfolders
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent
SOURCE_DIR = BASE_DIR / 'dataset_procesado'
OUTPUT_DIR = BASE_DIR / 'Dataset_Final'

if not SOURCE_DIR.exists():
    fallback_source = BASE_DIR / 'dataset'
    if fallback_source.exists():
        print(f"Aviso: no se encontró '{SOURCE_DIR.name}', usando '{fallback_source.name}' como fuente.")
        SOURCE_DIR = fallback_source
    else:
        raise FileNotFoundError(
            f"No se encontró la carpeta de entrada: '{SOURCE_DIR}' ni '{fallback_source}'."
        )

# Esta librería toma tu Dataset_Procesado y lo divide limpiamente
# 70% para entrenar, 15% para validar, 15% para pruebas finales.
splitfolders.ratio(str(SOURCE_DIR), 
                   output=str(OUTPUT_DIR), 
                   seed=42, 
                   ratio=(.7, .15, .15), 
                   group_prefix=None)

print("Dataset dividido con éxito en la carpeta 'Dataset_Final' (Train, Val, Test).")