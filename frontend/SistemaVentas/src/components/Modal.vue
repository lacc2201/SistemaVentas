<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <button class="close-btn" @click="close">&times;</button>
      <header>
        <h2>{{ title }}</h2>
      </header>
      <div class="modal-body">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const { title } = defineProps<{
  title: string
}>()

const emit = defineEmits(["close"])

const close = () => {
  emit("close")
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  position: relative; /* Necesario para posicionar el botón */
  background: white;
  padding: 1.5rem;
  border-radius: 10px;
  min-width: 300px;
  max-width: 500px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
}

header {
  margin-bottom: 1rem;
  text-align: center;
}

h2 {
  margin: 0;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background: transparent;
  color: black;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  line-height: 1;
}

.close-btn:hover {
  color: red;
}
</style>
