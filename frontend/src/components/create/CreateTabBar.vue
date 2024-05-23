<script setup>
import CreateProcessForm from './ProcessForm.vue';
import CreateStepForm from './StepForm.vue';

const tab = defineModel()
</script>

<template>
    <v-tabs
        v-model="tab"
        bg-color="#033A65"
        fixed-tabs
    >
        <v-tab value="Process">Create Process</v-tab>
        <v-tab value="Step">Create Step</v-tab>
        <v-tab v-for="n in 30" :key="n">{{ n }}</v-tab>
    </v-tabs>

    <v-window v-model="tab">
        <v-window-item value="Process"
        >
            <CreateProcessForm/>
        </v-window-item>
        <v-window-item value="Step"
        >
            <CreateStepForm/>
        </v-window-item>
    </v-window>
</template>
