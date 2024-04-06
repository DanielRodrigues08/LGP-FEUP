<script setup>
import SampleItem from './SampleItem.vue'
import SampleIcon from './icons/SampleIcon.vue'
</script>

<template>
  <SampleItem>
    <template #icon>
      <SampleIcon />
    </template>
    <template #heading>Sample Text</template>
  </SampleItem>
</template>
