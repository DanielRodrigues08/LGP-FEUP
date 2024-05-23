<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore();

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
  number: 1,
  id: null,
  title: '',
  description: '',
  deadline: 1,
  duration: 1,
  owner: null,
  backup: null,
  dependencies: [],
}])

let nextStepNumber = 2

function importStep(stepData) {
  for (const property in stepData) {
    steps.value[currentStep.value][property] = stepData[property]
  }
}

function addStep() {
  steps.value.push({
    number: nextStepNumber++, // display number on creation page
    id: null, // step id
    title: '',
    description: '',
    deadline: 1,
    duration: 1,
    owner: null,
    backup: null,
    dependencies: [], // list of step ids
  })
  
  // Manually reset form validity ( because it doesn't want to do it on its own >:c )
  valid.value = false
}

function removeStep(stepIndex) {
  steps.value.splice(stepIndex, 1)
}

function createProcess() {
  // const stepsWithPosition = Array.from(steps.value, (step, i) => ({...step, position: i+1}))

  for (let step of steps.value) delete step.number;
  const data = {
    title: process.value.title,
    description: process.value.desc,
    incomingSteps: steps.value
  }
  console.log(process.value)
  console.log(steps.value)

  loading.value = true

  // API call
  // console.log(authStore.authData())
  axios.post(`${import.meta.env.VITE_API_URL}/processes/create-with-steps`, data, { headers: authStore.authData() })
  .then(function (response) {
    console.log(response);
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