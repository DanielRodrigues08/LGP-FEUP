<script setup>
import { ref } from 'vue'
import axios from 'axios';
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore();

// get steps
const steps = await fetchSteps();
console.log(steps)
const show = ref(Array(steps.length).fill(false))
const search = ref('')

defineEmits(['importStep'])

async function fetchSteps() {
    try {
    // const response = await axios.get(`${import.meta.env.VITE_API_URL}/steps`);
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/steps`, {headers: authStore.authData()});
    console.log(response.data);
    return response.data;
    } catch (error) {
    console.error('Error fetching steps:', error);
    }
}

const stepsPerPage = 10
</script>

<template>
    <v-card class="h-100" elevation="0">
        <v-card-title class="pt-2">
            <v-row align="center">
                <v-col cols="4">
                    Step List
                </v-col>
                <v-col>
                    <v-text-field
                        v-model="search"
                        density="compact"
                        placeholder="Search"
                        prepend-inner-icon="mdi-magnify"
                        variant="outlined"
                        clearable
                        hide-details
                    ></v-text-field>
                </v-col>
            </v-row>
        </v-card-title>
        <v-card-text class="h-100">
            <v-data-iterator
                :items="steps"
                :items-per-page="stepsPerPage"
                :search="search"
                class="overflow-y-auto"
                style="height: 90%"
            >
                <template v-slot:default="{ items }">
                        <v-card
                            v-for="(item, index) in items"
                            class="pt-1 mr-2 mt-1 mb-3 "
                            :key="item"
                            :title="`${item.raw.title}`"
                            :subtitle="`Deadline: ${item.raw.deadline} day(s) | Duration: ${item.raw.duration}`"
                            variant="outlined"
                            color="primary"
                            hover
                        >
                        <v-card-text>
                            <v-row no-gutters>
                                <v-col cols="12">
                                    Owner: {{ item.raw.owner }}
                                </v-col>
                                <v-col cols="12">
                                    Backup: {{ item.raw.backup }}
                                </v-col>
                            </v-row>
                        </v-card-text>     
                        <v-card-actions>
                            <v-btn
                                :icon="show[index] ? 'mdi-chevron-up' : 'mdi-chevron-down'"
                                @click="show[index] = !show[index]"
                            ></v-btn>
                            <v-spacer></v-spacer>
                            <v-btn
                                icon='mdi-plus'
                                @click="$emit('importStep', item.raw)"
                            ></v-btn>
                        </v-card-actions>
                        <v-expand-transition>
                            <div v-show="show[index]">
                                <v-divider></v-divider>
                                <v-card-text>
                                    Description:
                                    <v-row no-gutters>
                                        {{ item.raw.description }}
                                    </v-row>
                                </v-card-text>
                            </div>
                        </v-expand-transition>
                    </v-card>
                </template>
                <template v-slot:footer="{ page, pageCount, prevPage, nextPage }">
                    <div class="d-flex align-center justify-center pa-4">
                    <v-btn
                        :disabled="page === 1"
                        density="compact"
                        icon="mdi-arrow-left"
                        variant="tonal"
                        rounded
                        @click="prevPage"
                    ></v-btn>

                    <div class="mx-2">
                        Page {{ page }} of {{ pageCount }}
                    </div>

                    <v-btn
                        :disabled="page >= pageCount"
                        density="compact"
                        icon="mdi-arrow-right"
                        variant="tonal"
                        rounded
                        @click="nextPage"
                    ></v-btn>
                    </div>
                </template>
            </v-data-iterator>
        </v-card-text>
    </v-card>
</template>
