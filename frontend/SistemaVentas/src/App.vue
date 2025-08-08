
<template>
  <header>
    <h1 class="title">Panel de Administración</h1>
      <nav>
        <RouterLink to="/vendedores">Vendedores</RouterLink>
        <RouterLink to="/productos">Productos</RouterLink>
        <RouterLink to="/ventas">Ventas</RouterLink>
      </nav>
  </header>

  <main>
    <RouterView />
  </main>
</template>

<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';
</script>
