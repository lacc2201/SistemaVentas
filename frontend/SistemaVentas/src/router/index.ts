import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ProductosView from '../views/ProductosView.vue'
import VendedoresView from '../views/VendedoresView.vue'
import VentasView from '../views/VentasView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    { path: '/vendedores', name: 'vendedores', component: VendedoresView },
    { path: '/productos', name: 'productos', component: ProductosView },
    { path: '/ventas', name: 'ventas', component: VentasView }
  ]
})

export default router
