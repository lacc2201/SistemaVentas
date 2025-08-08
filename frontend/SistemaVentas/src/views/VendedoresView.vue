<template>
  <h1>Vendedores</h1>

  <!-- Botón para abrir el modal -->
  <button @click="abrirModalNuevo" class="btn-nuevo">+ Nuevo Vendedor</button>

  <!-- Tabla de vendedores -->
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Correo</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="v in vendedores" :key="v.id_vendedor">
        <td>{{ v.id_vendedor }}</td>
        <td>{{ v.nombre }}</td>
        <td>{{ v.correo }}</td>
        <td>
          <button type="button" @click="eliminarVendedor(v.id_vendedor)" class="btn-eliminar">Eliminar</button>
          <button type="button" @click="abrirModalEditar(v)">Editar</button>
        </td>
      </tr>
    </tbody>
  </table>

  <!-- Modal con el formulario -->
  <Modal v-if="showModal" :title="modalTitle" @close="showModal = false">
    <form @submit.prevent="guardarYCerrar" class="form-grid">
      <label>
        Nombre
        <input v-model="nuevoVendedor.nombre" name="nombre" placeholder="Nombre del vendedor" required />
      </label>

      <label>
        Correo
        <input v-model="nuevoVendedor.correo" name="correo" type="email" placeholder="correo@ejemplo.com" required />
      </label>

      <button type="submit" class="btn-guardar">Guardar</button>
    </form>
  </Modal>
</template>

<script setup lang="ts">
import Modal from '@/components/Modal.vue'
import axios from 'axios'
import { onMounted, ref } from 'vue'

interface Vendedor {
  id_vendedor: number
  nombre: string
  correo: string
}

const vendedores = ref<Vendedor[]>([])
const nuevoVendedor = ref<Omit<Vendedor, 'id_vendedor'>>({
  nombre: '',
  correo: ''
})
const showModal = ref(false)
const modalTitle = ref('Nuevo Vendedor')
const vendedorEditando = ref<number | null>(null)

const cargarVendedores = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/vendedores')
    console.log('Datos del servidor (vendedores):', res.data)
    
    // Mapear los datos del servidor al formato que esperamos
    vendedores.value = res.data.map((vendedor: any) => ({
      id_vendedor: vendedor.id || vendedor.id_vendedor,
      nombre: vendedor.nombre,
      correo: vendedor.correo
    }))
    
    console.log('Vendedores mapeados:', vendedores.value)
  } catch (err) {
    console.error('Error cargando vendedores:', err)
  }
}

const abrirModalNuevo = () => {
  nuevoVendedor.value = { nombre: '', correo: '' }
  modalTitle.value = 'Nuevo Vendedor'
  vendedorEditando.value = null
  showModal.value = true
}

const abrirModalEditar = (vendedor: Vendedor) => {
  console.log('Vendedor recibido para editar:', vendedor)
  
  if (!vendedor.id_vendedor) {
    console.error('Vendedor sin ID válido:', vendedor)
    return
  }
  
  nuevoVendedor.value = {
    nombre: vendedor.nombre,
    correo: vendedor.correo
  }
  modalTitle.value = 'Editar Vendedor'
  vendedorEditando.value = vendedor.id_vendedor
  showModal.value = true
  console.log('Vendedor configurado para editar ID:', vendedor.id_vendedor)
}

const crearVendedor = async () => {
  try {
    console.log('Creando nuevo vendedor:', nuevoVendedor.value)
    await axios.post('http://localhost:8080/api/vendedores', nuevoVendedor.value)
    await cargarVendedores()
    console.log('Vendedor creado exitosamente')
  } catch (err) {
    console.error('Error creando vendedor:', err)
  }
}

const editarVendedor = async () => {
  if (!vendedorEditando.value) {
    console.error('No hay vendedor para editar')
    return
  }
  try {
    console.log('Editando vendedor ID:', vendedorEditando.value)
    console.log('Datos a enviar:', nuevoVendedor.value)
    await axios.put(`http://localhost:8080/api/vendedores/${vendedorEditando.value}`, nuevoVendedor.value)
    await cargarVendedores()
    console.log('Vendedor editado exitosamente')
  } catch (err) {
    console.error('Error editando vendedor:', err)
    if (err.response) {
      console.error('Respuesta del servidor:', err.response.data)
    }
  }
}

const guardarYCerrar = async () => {
  console.log('Guardando... Vendedor editando:', vendedorEditando.value)
  
  if (vendedorEditando.value !== null) {
    console.log('Ejecutando edición')
    await editarVendedor()
  } else {
    console.log('Ejecutando creación')
    await crearVendedor()
  }
  showModal.value = false
}

const eliminarVendedor = async (id: number | undefined) => {
  if (!id) {
    console.error('ID de vendedor no válido:', id)
    return
  }
  
  if (!confirm('¿Estás seguro de que deseas eliminar este vendedor?')) return
  
  try {
    console.log('Eliminando vendedor ID:', id)
    await axios.delete(`http://localhost:8080/api/vendedores/${id}`)
    await cargarVendedores()
    console.log('Vendedor eliminado exitosamente')
  } catch (err) {
    console.error('Error eliminando vendedor:', err)
    if (err.response) {
      console.error('Respuesta del servidor:', err.response.data)
    }
  }
}

onMounted(() => {
  cargarVendedores()
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