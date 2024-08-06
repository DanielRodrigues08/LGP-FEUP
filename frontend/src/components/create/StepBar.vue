<script setup>
import { ref, watch} from 'vue'
import StepForm from '@/components/create/StepForm.vue';

const steps = defineModel('steps', { required: true })
const tab = defineModel('tab', { required: true })
const length = ref(1)

const emit = defineEmits(['addStep', 'removeStep'])

watch(length, async(newLength) => {
    if (tab.value >= newLength)
        tab.value = newLength - 1
})

function addStep() {
    emit('addStep')
    length.value = steps.value.length
}

function removeStep(stepIndex) {
    emit('removeStep', stepIndex)
    length.value = steps.value.length
}

</script>

<template>
    <v-card style="height: 90%" elevation="0">
        <v-tabs
            v-model="tab"
            bg-color="#033A65"
            center-active
        >
            <v-tab
                v-for="(_, index) in steps"
                :key="index"
                :text="`Step ${index+1}`"
                :value="index"
            ></v-tab>
        </v-tabs>

        <v-col style="height: 95%" cols="12">
            <v-window class="h-100" v-model="tab">
                <v-window-item class="h-100" v-for="(stepData, index) in steps"
                    :key="index"
                    :value="index"
                >
                    <Suspense>
                        <StepForm
                            v-model:title="stepData.data.title"
                            v-model:desc="stepData.data.description"
                            v-model:deadline="stepData.data.deadline"
                            v-model:duration="stepData.data.duration"
                            v-model:owner="stepData.data.owner"
                            v-model:backup="stepData.data.backup"
                            v-model:dependencies="stepData.data.dependencies"
                            :dependencyStepList="Array.from({length: tab}, (_, i) => ({title: `Step ${i + 1}`, value: i+1}))"
                            :isLocked="stepData.metadata.locked"
                        ></StepForm>
                    </Suspense>
                </v-window-item>
            </v-window>
        </v-col>
    </v-card>

    <v-row class="text-center pt-2" align="center">
        <v-col class="fill-height">
            <v-btn
                class="w-100"
                :disabled="!length"
                text="Remove Step"
                elevation="0"
                color="red-darken-2"
                @click="removeStep(tab)"
            ></v-btn>
        </v-col>
        <v-col class="fill-height">
            <v-btn
                class="w-100"
                text="Add Step"
                elevation="0"
                color="green-darken-1"
                @click="addStep"
            ></v-btn>
        </v-col>
    </v-row>
</template>
