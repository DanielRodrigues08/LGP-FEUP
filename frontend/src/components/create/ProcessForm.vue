<script setup>
const procTitle = defineModel('title', { required: true })
const procDesc = defineModel('desc', { required: true })

const maxTitleLength = 40
const maxDescLength = 120

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
</script>

<template>
    <v-card title="Process" color="#033A65" variant="tonal">
        <v-card-text>
            <v-row>
                <v-col cols="12" sm="6">
                    <v-text-field
                        v-model="procTitle"
                        :counter="maxTitleLength"
                        :rules="titleRules"
                        label="Process title"
                        clearable
                        required
                    ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6">
                    <v-text-field
                        v-model="procDesc"
                        :counter="maxDescLength"
                        :rules="descRules"
                        label="Process description"
                        clearable
                    ></v-text-field>
                </v-col>
            </v-row>
        </v-card-text>
    </v-card>    
</template>
