<template>
  <h1>Productos</h1>

  <button @click="abrirModalNuevo">+ Nuevo Producto</button>

  <!-- Tabla en lugar de lista -->
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Precio</th>
        <th>Stock</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="p in productos" :key="p.id_producto">
        <td>{{ p.id_producto }}</td>
        <td>{{ p.nombre }}</td>
        <td>${{ p.precio }}</td>
        <td>{{ p.stock }}</td>
        <td>
          <button type="button" @click="eliminarProducto(p.id_producto)">Eliminar</button>
          <button type="button" @click="abrirModalEditar(p)">Editar</button>
        </td>
      </tr>
    </tbody>
  </table>

  <!-- Modal con formulario -->
  <Modal v-if="showModal" :title="modalTitle" @close="showModal = false">
    <form @submit.prevent="guardarYCerrar" class="form-grid">
      <label>
        Nombre
        <input v-model="nuevoProducto.nombre" name="nombre" placeholder="Nombre del producto" required />
      </label>

      <label>
        Precio Producto
        <input v-model.number="nuevoProducto.precio" name="precio" type="number" min="0" step="0.01" placeholder="0.00" required />
      </label>

      <label>
        Stock
        <input v-model.number="nuevoProducto.stock" name="stock" type="number" min="0" placeholder="0" required />
      </label>

      <button type="submit" class="btn-guardar">Guardar</button>
    </form>
  </Modal>
</template>

<script setup lang="ts">
import Modal from '@/components/Modal.vue'
import axios from 'axios'
import { onMounted, ref } from 'vue'

interface Producto {
  id_producto: number
  nombre: string
  precio: number
  stock: number
}

const productos = ref<Producto[]>([])
const nuevoProducto = ref<Omit<Producto, 'id_producto'>>({
  nombre: '',
  precio: 0,
  stock: 0
})
const showModal = ref(false)
const modalTitle = ref('Nuevo Producto')
const productoEditando = ref<number | null>(null)

const cargarProductos = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/productos')
    console.log('Datos del servidor:', res.data)
    
    // Mapear los datos del servidor al formato que esperamos
    productos.value = res.data.map((producto: any) => ({
      id_producto: producto.id || producto.id_producto,
      nombre: producto.nombre,
      precio: producto.precio,
      stock: producto.stock
    }))
    
    console.log('Productos mapeados:', productos.value)
  } catch (err) {
    console.error('Error cargando productos:', err)
  }
}

const abrirModalNuevo = () => {
  nuevoProducto.value = { nombre: '', precio: 0, stock: 0 }
  modalTitle.value = 'Nuevo Producto'
  productoEditando.value = null
  showModal.value = true
}

const abrirModalEditar = (producto: Producto) => {
  console.log('Producto recibido para editar:', producto)
  
  if (!producto.id_producto) {
    console.error('Producto sin ID válido:', producto)
    return
  }
  
  nuevoProducto.value = {
    nombre: producto.nombre,
    precio: producto.precio,
    stock: producto.stock
  }
  modalTitle.value = 'Editar Producto'
  productoEditando.value = producto.id_producto
  showModal.value = true
  console.log('Producto configurado para editar ID:', producto.id_producto)
}

const crearProducto = async () => {
  try {
    console.log('Creando nuevo producto:', nuevoProducto.value)
    await axios.post('http://localhost:8080/api/productos', nuevoProducto.value)
    await cargarProductos()
    console.log('Producto creado exitosamente')
  } catch (err) {
    console.error('Error creando producto:', err)
  }
}

const editarProducto = async () => {
  if (!productoEditando.value) {
    console.error('No hay producto para editar')
    return
  }
  try {
    console.log('Editando producto ID:', productoEditando.value)
    console.log('Datos a enviar:', nuevoProducto.value)
    await axios.put(`http://localhost:8080/api/productos/${productoEditando.value}`, nuevoProducto.value)
    await cargarProductos()
    console.log('Producto editado exitosamente')
  } catch (err) {
    console.error('Error editando producto:', err)
    if (err.response) {
      console.error('Respuesta del servidor:', err.response.data)
    }
  }
}

const guardarYCerrar = async () => {
  console.log('Guardando... Producto editando:', productoEditando.value)
  
  if (productoEditando.value !== null) {
    console.log('Ejecutando edición')
    await editarProducto()
  } else {
    console.log('Ejecutando creación')
    await crearProducto()
  }
  showModal.value = false
}

const eliminarProducto = async (id: number | undefined) => {
  if (!id) {
    console.error('ID de producto no válido:', id)
    return
  }
  
  if (!confirm('¿Estás seguro de que deseas eliminar este producto?')) return
  
  try {
    console.log('Eliminando producto ID:', id)
    await axios.delete(`http://localhost:8080/api/productos/${id}`)
    await cargarProductos()
    console.log('Producto eliminado exitosamente')
  } catch (err) {
    console.error('Error eliminando producto:', err)
    if (err.response) {
      console.error('Respuesta del servidor:', err.response.data)
    }
  }
}

onMounted(() => {
  cargarProductos()
})
</script>

<style scoped>
.form-grid {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

label {
  font-weight: 500;
  display: flex;
  flex-direction: column;
  font-size: 0.95rem;
}

input {
  margin-top: 0.25rem;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
}

input:focus {
  outline: none;
  border-color: #4cafef;
  box-shadow: 0 0 0 2px rgba(76, 175, 239, 0.2);
}

.btn-guardar {
  padding: 0.6rem 1rem;
  background-color: #4cafef;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
  align-self: flex-end;
}

.btn-guardar:hover {
  background-color: #3a9edb;
}

/* Estilos para la tabla */
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

th, td {
  padding: 0.75rem;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f5f5f5;
  font-weight: 600;
}

tbody tr:hover {
  background-color: #f9f9f9;
}

td button {
  margin-right: 0.5rem;
  padding: 0.25rem 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.875rem;
}

td button:hover {
  background-color: #e9e9e9;
}

td button:first-of-type {
  background-color: #3a9edb;
  color: white;
  border-color: #3a9edb;
}

td button:first-of-type:hover {
  background-color: #3a9edb;
}

td button:last-of-type {
  background-color: #3a9edb;
  color: white;
  border-color: #3a9edb;
}

td button:last-of-type:hover {
  background-color: #3a9edb;
}
</style>