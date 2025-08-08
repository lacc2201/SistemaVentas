<template>
  <h1>Ventas</h1>

  <button @click="abrirModalNueva">+ Nueva Venta</button>

  <!-- Cambié <ul> por <table> con estructura correcta -->
  <table>
    <thead>
      <tr>
        <th>ID Venta</th>
        <th>ID Producto</th>
        <th>ID Vendedor</th>
        <th>Fecha</th>
        <th>Cantidad</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="v in ventas" :key="v.id_venta">
        <td>{{ v.id_venta }}</td>
        <td>{{ v.id_producto }}</td>
        <td>{{ v.id_vendedor }}</td>
        <td>{{ v.fecha }}</td>
        <td>{{ v.cantidad }}</td>
        <td>
          <button type="button" @click="eliminarVenta(v.id_venta)" :disabled="!v.id_venta">Eliminar</button>
          <button type="button" @click="abrirModalEditar(v)">Editar</button>
        </td>
      </tr>
    </tbody>
  </table>

  <!-- Modal con el formulario -->
  <Modal v-if="showModal" :title="modalTitle" @close="showModal = false">
    <form @submit.prevent="guardarYCerrar" class="form-grid">
      <label>
        ID Producto
        <input v-model.number="nuevaVenta.id_producto" type="number" min="1" required />
      </label>

      <label>
        ID Vendedor
        <input v-model.number="nuevaVenta.id_vendedor" type="number" min="1" required />
      </label>

      <label>
        Fecha
        <input v-model="nuevaVenta.fecha" type="date" required />
      </label>

      <label>
        Cantidad
        <input v-model.number="nuevaVenta.cantidad" type="number" min="1" required />
      </label>

      <button type="submit" class="btn-guardar">Guardar</button>
    </form>
  </Modal>
</template>

<script setup lang="ts">
import Modal from '@/components/Modal.vue'
import axios from 'axios'
import { onMounted, ref } from 'vue'

interface VentaAPI {
  id: number
  producto: {
    id: number
    nombre: string
    precio: number
    stock: number
  }
  vendedor: {
    id: number
    nombre: string
    correo: string
  }
  fecha: string
  cantidad: number
}

interface Venta {
  id_venta?: number
  id_producto: number
  id_vendedor: number
  fecha: string
  cantidad: number
}

const showModal = ref(false)
const modalTitle = ref('Nueva Venta')
const ventas = ref<Venta[]>([])
const nuevaVenta = ref<Venta>({
  id_producto: 0,
  id_vendedor: 0,
  fecha: '',
  cantidad: 0
})

const cargarVentas = async () => {
  try {
    console.log('Cargando ventas...')
    const res = await axios.get<VentaAPI[]>('http://localhost:8080/api/ventas')
    console.log('Respuesta del servidor:', res.data)
    
    ventas.value = res.data.map((v): Venta => ({
      id_venta: v.id,
      id_producto: v.producto.id,
      id_vendedor: v.vendedor.id,
      fecha: v.fecha.split('T')[0],
      cantidad: v.cantidad
    }))
    
    console.log('Ventas procesadas:', ventas.value)
  } catch (err) {
    console.error('Error cargando ventas:', err)
    if (err.response) {
      console.error('Respuesta del servidor:', err.response.data)
    }
  }
}

const abrirModalNueva = () => {
  nuevaVenta.value = { id_producto: 0, id_vendedor: 0, fecha: '', cantidad: 0 }
  modalTitle.value = 'Nueva Venta'
  showModal.value = true
}

const abrirModalEditar = (venta: Venta) => {
  nuevaVenta.value = { ...venta }
  modalTitle.value = 'Editar Venta'
  showModal.value = true
}

const guardarYCerrar = async () => {
  try {
    // Validar datos antes de enviar
    if (!nuevaVenta.value.id_producto || !nuevaVenta.value.id_vendedor || !nuevaVenta.value.fecha || !nuevaVenta.value.cantidad) {
      console.error('Faltan datos requeridos:', nuevaVenta.value)
      alert('Por favor completa todos los campos')
      return
    }

    // Convertir la fecha a formato LocalDateTime (agregar la hora)
    const fechaConHora = `${nuevaVenta.value.fecha}T00:00:00`

    // Adaptar formato al que espera la API
    const payload = {
      producto: { id: nuevaVenta.value.id_producto },
      vendedor: { id: nuevaVenta.value.id_vendedor },
      fecha: fechaConHora,
      cantidad: nuevaVenta.value.cantidad
    }

    console.log('Payload a enviar:', payload)

    if (nuevaVenta.value.id_venta) {
      console.log('Editando venta ID:', nuevaVenta.value.id_venta)
      await axios.put(`http://localhost:8080/api/ventas/${nuevaVenta.value.id_venta}`, payload)
    } else {
      console.log('Creando nueva venta')
      await axios.post('http://localhost:8080/api/ventas', payload)
    }

    await cargarVentas()
    showModal.value = false
  } catch (err) {
    console.error('Error guardando venta:', err)
    console.error('Datos que se intentaron enviar:', nuevaVenta.value)
    
    if (err.response) {
      console.error('Respuesta del servidor:', err.response.data)
      console.error('Status:', err.response.status)
      alert(`Error del servidor: ${err.response.data?.message || 'Error desconocido'}`)
    } else {
      alert('Error de conexión con el servidor')
    }
  }
}

const eliminarVenta = async (id: number | undefined) => {
  if (!id) {
    console.error('ID de venta no válido para eliminar')
    return
  }
  if (!confirm('¿Seguro que deseas eliminar esta venta?')) return
  try {
    await axios.delete(`http://localhost:8080/api/ventas/${id}`)
    await cargarVentas()
  } catch (err) {
    console.error('Error eliminando venta:', err)
  }
}

onMounted(() => {
  cargarVentas()
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