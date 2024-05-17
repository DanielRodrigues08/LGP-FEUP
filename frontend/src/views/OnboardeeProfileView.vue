<template>
    <main>
        <div v-if="onboardee" class="parent">
            <div class="PersonalInformation">
                <h2>Onboardee Personal Information</h2>
                <h3>{{ onboardee.name }}</h3>
                <p>Email: {{ onboardee.email }}</p>
                <p>Phone: {{ onboardee.phoneNumber }}</p>
                <p>Gender: {{ onboardee.gender }}</p>
                <p>Country Born: {{ onboardee.nationality }}</p>
                <p>Annual Salary: {{ onboardee.annualSalary }}</p>
                <p>Payroll Number: {{ onboardee.payrollNumber }}</p>
                <p>Start Date: {{ onboardee.startDate }}</p>
                <p>Status: 
                    <span :class="onboardee.state">{{ onboardee.state }}</span>
                </p>
                <Button class="custom-button" icon="pi pi-pencil" rounded aria-label="Filter" />
            </div>
            <div class="emptyColumn"> </div>
        </div>
        <div v-else>
            <h1>Onboardee not found</h1>
        </div>
    </main>
</template>

<script>
import axios from 'axios';
import Button from "primevue/button"

export default {
    components: {
      Button,
    },
    data() {
        return {
            onboardee: null
        };
    },
    mounted() {
        const onboardeeId = this.$route.params.id;
        this.fetchOnboardeeById(onboardeeId);
    },
    methods: {
        async fetchOnboardeeById(onboardeeId) {
            try {
                const response = await axios.get(`http://localhost:8081/api/onboardees/${onboardeeId}`);
                this.onboardee = response.data;
            } catch (error) {
                console.error('Error fetching user:', error);
            }
        }
    }
};
</script>

<style scoped>
.parent {
    display: grid;
    margin-top: 0.3em;
    grid-template-columns: 1fr 3fr;
    height: calc(100vh - 3.55rem);
}

.PersonalInformation {
    background-color: #F9F9F9;
    padding: 1em;
    overflow-y: auto;
}

.PersonalInformation h3 {
    padding: 1em;
    padding-left: 2em;
    font-weight: 600;
    color: #    ;
    font-size: 1em;
}

.PersonalInformation h2 {
    font-size: 1.2em;
    font-weight: bold;
    margin-top: 1em;
    margin-bottom: 0.5em;
    text-align: center;
}

.PersonalInformation p {
    font-size: 1em;
    padding: 1em;
    padding-left: 2em;
    padding-right: 2em;
}

.INCOMING {
    color: green;
}

.ONGOING {
    color: #1976D2;
}

.COMPLETED {
    color: purple;
}

.ABORTED {
    color: #C81E1E;
}

.emptyColumn {
    background-color: white;
}

.custom-button {
    background-color: #033A65 !important;
    color: white !important;
    margin: 2em;
    border: none;
}
</style>
