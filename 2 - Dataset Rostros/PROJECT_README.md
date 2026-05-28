# Proyecto: Reconocimiento Facial (Dataset Rostros)

## Resumen

Este proyecto implementa un pipeline de reconocimiento facial por clasificación de identidades usando transferencia de aprendizaje (MobileNetV2). El flujo completo va desde la captura de imágenes, recorte y limpieza, división en train/val/test, entrenamiento del modelo y prueba en tiempo real con webcam.

---

## Estructura principal

- `dataset/` — imágenes crudas organizadas por persona: `dataset/<NombrePersona>/*`.
- `dataset_procesado/` — (opcional) caras recortadas y normalizadas generadas por `recortarimg.py`.
- `Dataset_Final/` — salida de `split_data.py` con subcarpetas `train/`, `val/`, `test/` (cada una contiene subcarpetas por clase).
- `modelo_reconocimiento_facial.keras` — modelo Keras guardado después del entrenamiento.
- Scripts clave:
  - `rostro.py` — captura imágenes desde webcam y las guarda en `dataset/<persona>`.
  - `recortarimg.py` — recorta caras con Haar Cascade y guarda en `dataset_procesado/<persona>`.
  - `split_data.py` — divide `dataset_procesado` (o `dataset` si no existe) en `Dataset_Final` usando `splitfolders`.
  - `train_model.py` — entrena el clasificador usando `MobileNetV2` + cabeza densa y guarda el modelo.
  - `probar_modelo.py` — carga el modelo y ejecuta inferencia en tiempo real con la webcam.

---

## Datos y preprocesamiento

1. Captura
   - `rostro.py` abre la cámara, detecta caras con `haarcascade_frontalface_default.xml` y guarda fotos en `dataset/<persona>`.

2. Recorte y limpieza
   - `recortarimg.py` detecta y recorta las caras de cada imagen, redimensiona a un tamaño fijo (ej. 60×60 en el script) y guarda los recortes en `dataset_procesado/<persona>`.
   - Nota: por defecto `recortarimg.py` procesa una carpeta nombrada por la variable `nombre_actor`. Se puede modificar para procesar todas.

3. División
   - `split_data.py` usa `splitfolders` para crear `Dataset_Final/train`, `Dataset_Final/val`, `Dataset_Final/test`.
   - Si `dataset_procesado` no existe, se usa `dataset` directamente.

---

## Modelo y entrenamiento

- Backbone: `MobileNetV2` (preentrenado en ImageNet) con `include_top=False`.
- Head: `GlobalAveragePooling2D` → `Dense(128)` → `Dropout` → `Dense(num_clases, softmax)`.
- Estrategia: transfer learning (base congelada, entrenar la cabeza). Opcional: descongelar capas superiores para fine-tuning.
- Entrada: imágenes redimensionadas (ej. 160×160), normalizadas con `Rescaling`.
- Loss: `sparse_categorical_crossentropy` o `categorical_crossentropy` según labels; optimizador típico: `Adam`.
- Guardado: modelo completo en `modelo_reconocimiento_facial.keras`.

Resultados observados (ejecución reciente)

- Épocas: 15
- Métrica final (validation): `val_accuracy: 0.8364`, `val_loss: 0.4185`.
- Estructura de parámetros: params totales ≈ 2.42M, entrenables ≈ 166k (solo cabeza entrenada).

---

## Inferencia / Uso en tiempo real

- `probar_modelo.py` carga `modelo_reconocimiento_facial.keras`, obtiene la lista de clases desde las subcarpetas de `Dataset_Final/train`, abre la webcam, detecta la cara (Haar cascade), redimensiona y normaliza la ROI y llama `model.predict()`.
- La predicción devuelta es la clase con mayor probabilidad y la confianza asociada.

---

## Dependencias y entorno recomendado

- Python 3.11 (en este workspace se creó un venv en `3 - Machine Learning/code/python/INTELIGENCIA-ARTIFICIAL/.venv-py311`).
- Paquetes principales:
  - `tensorflow` (2.21.0 en el entorno usado)
  - `opencv-python`
  - `numpy`
  - `pandas` (scripts auxiliares)
  - `split-folders` (paquete `splitfolders`)

Instalación (ejemplo con `pip`):

```bash
python -m pip install tensorflow opencv-python numpy pandas split-folders
```

Notas sobre Windows

- TensorFlow >= 2.11 en Windows no usa GPU nativo; si necesitas GPU, usar WSL2 o TensorFlow-DirectML.
- Verás advertencias de oneDNN y CPU instructions, que son normales.

---

## Comandos de uso rápido

1. Recortar caras (ejecutar desde `2 - Dataset Rostros`):

```powershell
& "<ruta-a-venv-py311>\Scripts\python.exe" recortarimg.py
```

(editar `nombre_actor` en el script o modificar el script para procesar todas las carpetas).

2. Dividir datos:

```powershell
& "<ruta-a-venv-py311>\Scripts\python.exe" split_data.py
```

3. Entrenar:

```powershell
& "<ruta-a-venv-py311>\Scripts\python.exe" train_model.py
```

4. Probar en tiempo real:

```powershell
& "<ruta-a-venv-py311>\Scripts\python.exe" probar_modelo.py
```

(Reemplaza `<ruta-a-venv-py311>` por `C:\Users\carlo\OneDrive\Escritorio\ia\INTELIGENCIA-ARTIFICIAL\3 - Machine Learning\code\python\INTELIGENCIA-ARTIFICIAL\.venv-py311` u otro entorno activo.)

---

## Buenas prácticas y recomendaciones

- Guardar un `class_index.json` con el mapeo `{clase: indice}` cuando guardes el modelo, para reproducibilidad.
- Evaluar con `Dataset_Final/test` y generar matriz de confusión + F1 score por clase antes de desplegar.
- Para reconocimiento escalable (muchas personas), considerar un sistema de embeddings (FaceNet, ArcFace) en lugar de una softmax por clase.
- Aumentación de datos: rotaciones, flips horizontales, ajustes de brillo/contraste para robustez.
- Normalizar nombres de carpetas (evitar espacios o caracteres raros) para consistencia.

---

## Archivos importantes (ubicación relativa)

- [rostro.py](rostro.py)
- [recortarimg.py](recortarimg.py)
- [split_data.py](split_data.py)
- [train_model.py](train_model.py)
- [probar_modelo.py](probar_modelo.py)
- `modelo_reconocimiento_facial.keras`

---

## Próximos pasos sugeridos

- Ejecutar `recortarimg.py` para procesar todas las carpetas y generar `dataset_procesado/`.
- Volver a ejecutar `split_data.py` y entrenar con más épocas o con fine-tuning.
- Ejecutar evaluación sobre `Dataset_Final/test` y generar reportes.

---

Si quieres, genero ahora un script que procese automáticamente todas las carpetas en `dataset` y cree `dataset_procesado` (recomendado). También puedo ejecutar ese script por ti.
