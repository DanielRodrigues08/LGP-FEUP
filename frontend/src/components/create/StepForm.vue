<script setup>
import axios from 'axios';import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore();

const stepOwner = defineModel('owner', { required: true })
const stepBackup = defineModel('backup', { required: true })
const stepTitle = defineModel('title', { required: true })
const stepDesc = defineModel('desc', { required: true })
const stepDeadline = defineModel('deadline', { required: true })
const stepDuration = defineModel('duration', { required: true })
const stepDependencies = defineModel('dependencies', { required: true })

defineProps(['dependencyStepList', 'isLocked'])

const minDeadline = 1
const maxDeadline = 90
const minDuration = 0
const maxDuration = 90
const maxTitleLength = 40
const maxDescLength = 120
const fieldDensity = "default"

const personnel = await fetchUsers()
//console.log(personnel)

async function fetchUsers() {
    try {
        // console.log(authStore.authData())
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/users`, {headers: authStore.authData()});
        
        let users_trimmed = [];
        for (let user of response.data) {
            // users_trimmed.push([user.id, user.name])
            users_trimmed.push({id: user.id, name: user.name})
        }
        
        //console.log(users_trimmed);
        return users_trimmed;
    } catch (error) {
        console.error('Error fetching steps:', error);
    }
}

const titleRules = [
    value => {
        if (value) return true
        return 'Title is required.'
    },
    value => {
        if (value?.length <= maxTitleLength) return true
        return `Title must be less than ${maxTitleLength} characters.`
    },
]
const descRules = [
    value => {
        if (value?.length <= maxDescLength) return true
        return `Description must be less than ${maxDescLength} characters.`
    },
]
const deadlineRules = [
    value => {
        if (value) return true
        return 'Deadline must be set.'
    },
    value => {
        if (value >= minDeadline && value <= maxDeadline) return true
        return `Deadline must be between ${minDeadline} and ${maxDeadline} days.`
    }

]
const durationRules = [
    value => {
        if (value >= minDuration && value <= maxDuration) return true
        return `Duration must be between ${minDuration} and ${maxDuration} days.`
    }

]
const personnelRules = [
    value => {
        if (value) return true
        return 'A person must be selected.'
    },  
]
</script>

<template>
    <v-row class="fill-height overflow-y-auto my-auto">
        <v-col cols="12" sm="6">
            <v-text-field
                v-model="stepTitle"
                :counter="maxTitleLength"
                :rules="titleRules"
                :density="fieldDensity"
                :readonly="isLocked"
                :clearable="!isLocked"
                label="Step title"
                required
            ></v-text-field>
        </v-col>

        <v-col cols="12" sm="6">
            <v-select
            v-model="stepDependencies"
            :items="dependencyStepList"
            :density="fieldDensity"
            label="Depends on"
            item-title="title"
            item-value="value"
            chips
            multiple
            closable-chips
            >
            </v-select>
        </v-col>
        
        <v-col cols="12">
            <v-text-field
                v-model="stepDesc"
                :counter="maxDescLength"
                :rules="descRules"
                :density="fieldDensity"
                :readonly="isLocked"
                :clearable="!isLocked"
                label="Step description"
            ></v-text-field>
        </v-col>

        <v-col cols="12" sm="6">
            <v-row align="center">
                <v-col cols="12" sm="7">
                    <v-text-field
                        v-model="stepDeadline"
                        :rules="deadlineRules"
                        :density="fieldDensity"
                        :readonly="isLocked"
                        :clearable="!isLocked"
                        type="number"
                        label="Deadline in"
                        suffix="day(s)"
                        hide-spin-buttons
                    ></v-text-field>
                </v-col>
                <v-col cols="12" sm="5">
                    <v-slider
                        v-model="stepDeadline"
                        :max=maxDeadline
                        :min=minDeadline
                        :step="1"
                        :readonly="isLocked"
                    >
                    </v-slider>
                </v-col>
            </v-row>
        </v-col>

        <v-col cols="12" sm="6">
            <v-row align="center">
                <v-col cols="12" sm="7">
                    <v-text-field
                        v-model="stepDuration"
                        :rules="durationRules"
                        :density="fieldDensity"
                        :readonly="isLocked"
                        :clearable="!isLocked"
                        type="number"
                        label="Duration:"
                        suffix="day(s)"
                        hide-spin-buttons
                    ></v-text-field>
                </v-col>
                <v-col cols="12" sm="5">
                    <v-slider
                        v-model="stepDuration"
                        :max=maxDuration
                        :min=minDuration
                        :step="1"
                        :readonly="isLocked"
                    >
                    </v-slider>
                </v-col>
            </v-row>
        </v-col>

        <v-col cols="12" sm="6">
            <v-autocomplete
                variant="outlined"
                v-model="stepOwner"
                :items="personnel"
                :rules="personnelRules"
                :density="fieldDensity"
                :readonly="isLocked"
                :clearable="!isLocked"
                item-title="name"
                item-value="id"
                label="Step owner"
                required
            >
            </v-autocomplete>
        </v-col>
    
        <v-col cols="12" sm="6">
            <v-autocomplete
                variant="outlined"
                v-model="stepBackup"
                :items="personnel"
                :rules="personnelRules"
                :density="fieldDensity"
                :readonly="isLocked"
                :clearable="!isLocked"
                item-title="name"
                item-value="id"
                label="Step backup"
                required
            >
            </v-autocomplete>
        </v-col>

    </v-row>
</template>
