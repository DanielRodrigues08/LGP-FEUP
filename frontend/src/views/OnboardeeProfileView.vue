<template>
    <main>

        <div v-if="onboardee" class="parent">
            <div class="div1">
                <div class="OnboardName">
                    <h2>{{ onboardee.name }}</h2>
                    <p>Onboardee</p>
                </div>

            </div>
            <div class="PersonalInformation">
                <h2>Personal Information</h2>
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

            </div>
            <div class="div3"> </div>
        </div>

        <div v-else>
            <h1>Onboardee not found</h1>
        </div>
    </main>
</template>

<script>
import axios from 'axios';

export default {
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
                console.log('onboardeeId:', onboardeeId)
                const response = await axios.get(`http://localhost:8081/onboardees/${onboardeeId}`);
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
    grid-template-columns: 1fr 3fr;
    grid-template-rows: 1fr 2fr;
    grid-column-gap: 0px;
    grid-row-gap: 0px;
}

.div1 {
    grid-area: 1 / 1 / 2 / 6;
    background-color: #033A65;
    height: 20em;
    display: flex;
    padding: 3em;
}

.div1 img {
    width: 14em;
    height: 14em;
    box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.5);
}

.div1 .OnboardName {
    margin-left: 2em;
    color: white;
    align-self: center;
    font-size: 1.5em;
    padding: 1em;


}

.OnboardName h2 {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 1em;
}

.PersonalInformation {
    grid-area: 2 / 1 / 6 / 2;
    background-color: #F9F9F9;
    /*background-color: #F9F9F9;*/
    height: 100%;
}

.PersonalInformation h2 {
    font-size: 1.25em;
    font-weight: bold;
    margin-top: 2em;
    margin-bottom: 1em;
    padding: 1em;
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

.div3 {
    grid-area: 2 / 2 / 3 / 3;
    background-color: white;
}
</style>