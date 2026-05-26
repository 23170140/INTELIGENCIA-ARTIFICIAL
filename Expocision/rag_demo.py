import os
from dotenv import load_dotenv
from langchain_community.vectorstores import FAISS

load_dotenv()
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_classic.chains import RetrievalQA
from langchain_core.documents import Document
from langchain_huggingface import HuggingFaceEmbeddings


print("1. Inicializando modelo de Embeddings (Local)...")
embeddings = HuggingFaceEmbeddings(model_name="all-MiniLM-L6-v2")

print("2. Creando documentos de conocimiento interno (Simulación)...")
# Aquí definimos la información privada que el LLM normalmente NO conoce
documentos_empresa = [
    Document(page_content="El servidor X sufre fallos cuando la temperatura del rack supera los 45 grados. Los síntomas incluyen reinicios aleatorios y pérdida de paquetes en el puerto 80."),
    Document(page_content="Para solucionar el fallo del servidor X, se debe activar el ventilador secundario mediante el comando 'sys-cool --force' y verificar los logs en /var/log/thermal."),
    Document(page_content="El protocolo de seguridad de la empresa prohíbe apagar el servidor X sin autorización previa del CTO.")
]

print("3. Indexando documentos en la base de datos vectorial (FAISS)...")
# En lugar de cargar un archivo, creamos el índice en memoria a partir de los textos anteriores
db = FAISS.from_documents(documentos_empresa, embeddings)

print("4. Configurando el componente Retriever...")
# Configuramos para que busque los 2 fragmentos de texto más relevantes
retriever = db.as_retriever(search_kwargs={"k": 2})

print("5. Creando la cadena RAG acoplada a Gemini-Flash-Latest...")
rag_chain = RetrievalQA.from_chain_type(
    llm=ChatGoogleGenerativeAI(model="gemini-flash-latest", temperature=0),
    chain_type="stuff",  # Introduce los textos recuperados directamente en el prompt
    retriever=retriever
)

print("\n--- Iniciando consulta del usuario ---")
pregunta = "¿Cuáles son los síntomas del fallo en el servidor X?"
print(f"Pregunta: {pregunta}\n")

# Ejecución del pipeline RAG
respuesta = rag_chain.invoke(pregunta)

print("--- Respuesta Generada por RAG ---")
print(respuesta["result"])
