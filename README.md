# 🚀 Flujo de Trabajo del Repositorio

Para mantener la estabilidad del proyecto, **la rama `main` está protegida**. 
Esto significa que nadie puede hacer un `push` directo a main. 
Todos los cambios deben pasar obligatoriamente por una Revisión de Código a través de un **Pull Request (PR)**.

A continuación, detallo los pasos exactos que hay que seguir 
en el terminal (gitbash o en el terminal de linux) para subir tus cambios correctamente.

---

## 🛠️ Pasos para trabajar

### 1. Obtener la última versión de la rama`main`
Antes de empezar a trabajar hay que obtener la última versión de la rama`main`.

```bash
git checkout main
git pull origin main
```

### 2. Crear una rama nueva con un nombre descriptivo
Crear una rama específica para la tarea que vamos a realizar. 
El nombre debe estar relacionado con los cambios o con la tarea que vamos a hacer.
```bash
git checkout -b tu-nombre-de-rama
```

### 3. Realizar los cambios y luego hacer el commit
Ahora podemos arrancar a trabajar. Cuando terminamos ejecutamos esto:
```bash
git add .
git commit -m "Explicación breve y clara de los cambios realizados"
```

### 4. Subir la rama a GitHub
Dado que es una rama nueva, hay que indicarle a Git dónde subirla por primera vez:
```bash
git push --set-upstream origin tu-nombre-de-rama
```

### 5. Crear el Pull Request (PR) en GitHub

Una vez que el comando anterior termine con éxito:

1) Entrar al repositorio en GitHub.

2) Hacer click en el botón *"Compare & pull request"*.

3) Añadir una descripción de lo que se hizo, puede ser la misma del commit. 
Seleccionar a los revisores y hacer clic en *"Create pull request"*.


## Reglas Importantes

> ❌ **Nunca hacer git push origin main**. La rama está protegida y no permite un push directo.

> 🏷️ **Nombres de ramas**: Evitar nombres genéricos como cambios, arreglos o rama1. Ser específico.

> 👥 **No auto-aprobarse los PR**: La idea de trabajar con PRs es evitar que alguien destruya el código 
y tengamos que trabajar el doble. Por esto es que otros tienen que revisar su código.