<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'; 

const authStore = useAuthStore();
const router = useRouter();

import ProcessForm from '@/components/create/ProcessForm.vue';
import StepBar from '@/components/create/StepBar.vue';
import StepList from '@/components/create/StepList.vue';

const valid = ref(false)
const loading = ref(false)
const currentStep= ref(0)
const process = ref({
  title: '',
  desc: ''
  // onboardee: null,
})
const steps = ref([{
  metadata: {
    number: 1,
    locked: false,
  },
  data: {
    id: null,
    title: '',
    description: '',
    deadline: 1,
    duration: 1,
    owner: null,
    backup: null,
    dependencies: [],
  }
}])
let nextStepNumber = 2

function addStep() {
  steps.value.push({
      metadata: {
      number: nextStepNumber++,
      locked: false,
    },
    data: {
      id: null,
      title: '',
      description: '',
      deadline: 1,
      duration: 1,
      owner: null,
      backup: null,
      dependencies: [],
    }
  })
  
  // Manually reset form validity ( because it doesn't want to do it on its own >:c )
  valid.value = false
}

function removeStep(stepIndex) {
  steps.value.splice(stepIndex, 1)
}

function importStep(stepData) {
  for (const property in stepData) {
    steps.value[currentStep.value].data[property] = stepData[property]
  }
  steps.value[currentStep.value].metadata.locked = true
}

function createProcess() {
  // const stepsWithPosition = Array.from(steps.value, (step, i) => ({...step.data, position: i+1}))

  const data = {
    title: process.value.title,
    description: process.value.desc,
    incomingSteps: Array.from(steps.value, (step) => step.data)
  }
  loading.value = true

  // API call
  axios.post(`${import.meta.env.VITE_API_URL}/processes/create-with-steps`, data, { headers: authStore.authData() })
  .then(function (response) {
    router.push('/processes');
  })
  .catch(function (error) {
    console.log(error);
  });
  loading.value = false
}

// get users

</script>

<template>
  <div class="wrapper">
    <v-container fluid class="fill-height">
      <v-form class="w-100" v-model="valid" @submit.prevent="createProcess">
        <v-card color="#033A65" variant="tonal">
          <v-card-text class="h-100">
            <ProcessForm
              v-model:title="process.title"
              v-model:desc="process.desc"
            ></ProcessForm>
              <v-row class="my-2 step-section">
                <v-col class="h-100" cols="3">
                  <Suspense>
                    <StepList
                      @importStep="importStep">
                    </StepList>
                  </Suspense>
                </v-col>
                <v-col class="h-100" cols="9">
                  <StepBar
                    v-model:tab="currentStep"
                    v-model:steps="steps"
                    @addStep="addStep"
                    @removeStep="removeStep"
                  ></StepBar>
                </v-col>
              </v-row>
            <v-btn
              :loading="loading"
              :disabled="!valid"
              color="#033A65"
              class="mt-2"
              text="Create"
              type="submit"
              block
            ></v-btn> 
          </v-card-text>
        </v-card>
      </v-form>
    </v-container>
  </div>
</template>

<style scoped>
  .wrapper {
    height: 90vh;
  }
  .step-section {
    height: 68vh;
  }
</style>
