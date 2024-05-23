<template>
    <main>
        <div class="onboardee-main">
            <div v-if="onboardee" class="onboardee-name">
                <span class="onboardee-primary">{{ onboardee.name }}</span>
                <span>Onboardee</span>
            </div>
            <div v-else class="onboardee-name">
                <span class="onboardee-primary">Not found</span>
                <span>Onboardee</span>
            </div>
        </div>
        <div class="info-main">
            <div class="info-onboardee">
                <div v-if="onboardee" class="parent-onboardee">
                    <div class="PersonalInformation">
                        <h2>Personal Information</h2>
                        <p>
                            Email: <a class="email-link" href="#" @click.prevent="sendEmailToOnboardee">{{ onboardee.email }}</a>
                        </p>
                        <p>Phone: {{ onboardee.phoneNumber }}</p>
                        <p>Gender: {{ onboardee.gender }}</p>
                        <p>Country Born: {{ onboardee.nationality }}</p>
                        <p>Annual Salary: {{ onboardee.annualSalary }}</p>
                        <p>Payroll Number: {{ onboardee.payrollNumber }}</p>
                        <p>Start Date: {{ onboardee.startDate.toLocaleString("en-UK", { year: 'numeric', month: 'numeric', day: 'numeric' }) }}</p>
                        <p>Status: 
                            <span :class="onboardee.state">{{ onboardee.state }}</span>
                        </p>
                        <Button class="custom-button" v-if="canEdit" icon="pi pi-pencil" rounded aria-label="Filter" @click="showEditDialog = true" />
                    </div>
                    <div class="emptyColumn"> </div>
                </div>
            </div>
            <div v-if="onboardee" class="info-process">
                <span class="process-name"
                v-if="process">{{ process.steps.filter(step => step.stepInfoStatus == "COMPLETED").length == process.steps.length ? 'Finished' : 'Active' }}
                    Process: <span>{{ process.processTitle }}</span></span>
                <div class="info-process" v-if="process">
                <Stepper orientation="vertical" class="stepper">
                        <StepperPanel v-for="(step, index) in this.process.steps" v-bind:key="index" :header="'&nbsp;'+step.stepTitle">
                        <template #content="{ prevCallback, nextCallback }">
                            <div class="step-content flex flex-column">
                                <div class="process-content border-2 border-solid surface-border">
                                    <div class="step-title-row">
                                        <div class="step-row">
                                            <div class="step-title-desc">
                                                {{ step.stepDescription }}
                                            </div>
                                            <div class="flex flex-column align-items-end">
                                                <div class="font-bold text-black pt-2">
                                                    Deadline: 
                                                    <span>
                                                        {{ new Date(step.dueDate).toLocaleDateString("en-UK") }}
                                                        <span class="date-color">
                                                            ({{ calculateDaysLeft(new Date(step.dueDate)) == 1 ? '1 day left' : calculateDaysLeft(new Date(step.dueDate)) + ' days left' }})
                                                        </span>
                                                    </span>
                                                </div>
                                                <div class="font-bold text-black pt-2">
                                                    Duration: 
                                                    <span>{{ step.duration == 1 ? '1 day' : step.duration + ' days' }}</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="step-title-owners">
                                            <div class="font-bold text-black">
                                                Owner: <a href="#" class="email-link" @click.prevent="sendEmailToEmployee(step.ownerId)">{{ step.ownerName }}</a>
                                            </div>
                                            <div class="font-bold text-black pt-1">
                                                Backup: <a href="#" class="email-link" @click.prevent="sendEmailToEmployee(step.backupId)">{{ step.backupName }}</a>
                                            </div>
                                            <div v-if="step.dependencies.length > 0" class="font-bold text-black pt-3">
                                                Depends on: <span>{{ displayDependencies(step).join(', ')}}</span>
                                            </div>
                                            <div v-else/>
                                        </div>
                                    </div>
                                    <div class="flex flex-column">
                                        <div class="flex flex-row justify-content-between align-items-end">
                                            <div class="step-title-owners">
                                                <span class="font-bold">Step Info:</span>
                                                <form @submit.prevent="postStepInfo(step)" class="step-title-owners-row">
                                                    <div class="form-group">
                                                        <label for="stepInfo"/>
                                                        <InputText :disabled="step.stepInfoStatus == 'COMPLETED'" id="stepInfo" size="small" placeholder="Step Info" v-model="step.stepInfodDescription" small />
                                                    </div>
                                                    <Button
                                                    :disabled="step.stepInfoStatus == 'COMPLETED' || editInfo == true"
                                                    class="ml-2 company-color"
                                                    :icon="(editInfo == false ? 'pi pi-check': 'pi pi-spin pi-spinner')" type="submit" size="small"  aria-label="Submit"style="color:white"/>
                                                </form>
                                            </div>
                                            <div class="flex flex-column justify-content-end">
                                                <div class="flex font-bold text-black mb-3 w-full justify-content-end">
                                                    Status: &nbsp;
                                                    <span>
                                                        {{ step.stepInfoStatus.replace('_'," ") }}
                                                    </span>
                                                </div>
                                                <div class="flex flex-row">
                                                    <Button v-if="(step.stepInfoStatus == 'IN_PROGRESS')"
                                                    class="mr-3"
                                                    label="&nbsp; Stop"
                                                    icon="pi pi-stop-circle"
                                                    iconPos="left"
                                                    severity="secondary"
                                                    @click="changeStepStatus(step.stepInfoId, 'NOT_STARTED')"/>

                                                    <Button v-else
                                                    class="mr-3 company-color"
                                                    label="&nbsp; Start"
                                                    icon="pi pi-play-circle"
                                                    iconPos="left"
                                                    @click="changeStepStatus(step.stepInfoId, 'IN_PROGRESS')"
                                                    :disabled="step.stepInfoStatus == 'COMPLETED'"
                                                    style="color:white"
                                                    />
                                                    
                                                    <Button label="&nbsp; Complete" severity="success" icon="pi pi-check-circle" iconPos="left"
                                                    :disabled="step.stepInfoStatus != 'IN_PROGRESS'"
                                                    @click="changeStepStatus(step.stepInfoId, 'COMPLETED')"  />
                                                    <Button class="ml-3" label="&nbsp; Abort" severity="danger" icon="pi pi-times-circle" iconPos="left"
                                                    :disabled="step.stepInfoStatus == 'ABORTED' || step.stepInfoStatus == 'COMPLETED'"
                                                    @click="changeStepStatus(step.stepInfoId, 'ABORTED')" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div v-if="process.steps.length == 1">
                                <div class="flex pt-4 justify-content-end"/>
                            </div>
                            <div v-else-if="index == 0">
                                <div class="flex pt-4 justify-content-end">
                                    <Button
                                    label="Next&nbsp;"
                                    icon="pi pi-arrow-right"
                                    iconPos="right"
                                    @click="nextCallback"
                                    link
                                    />
                                </div>
                            </div>
                            <div v-else-if="index < process.steps.length - 1">
                                <div class="flex pt-4 justify-content-between">
                                    <Button
                                    label="&nbsp;Back"
                                    severity="secondary"
                                    icon="pi pi-arrow-left"
                                    @click="prevCallback"
                                    link
                                    />
                                    <Button
                                    label="Next&nbsp;"
                                    icon="pi pi-arrow-right"
                                    iconPos="right"
                                    @click="nextCallback"
                                    link
                                    />
                                </div>
                            </div>
                            <div v-else>
                                <div class="flex pt-4 justify-content-between">
                                    <Button
                                    label="&nbsp;Back"
                                    severity="secondary"
                                    icon="pi pi-arrow-left"
                                    @click="prevCallback"
                                    link
                                    />
                                </div>
                            </div>
                        </template>
                    </StepperPanel>
                </Stepper>
                </div>
                <div v-else class="info-process-not-found">
                    <p class="not-found">
                        <i class="pi pi-info-circle"></i>&nbsp;
                        This onboardee does not have an active process.
                    </p>
                </div>
            </div>
        </div>

        <Dialog v-model:visible="showEditDialog" header="Edit Onboardee" :closable="false" :draggable="false">
            <div v-if="editOnboardee" class="parent-dialog">
                <div class="flex align-items-center gap-5 mb-3">
                    <label for="name" class="font-semibold w-6rem">Name</label>
                    <InputText id="name" v-model="editOnboardee.name" class="flex-auto" autocomplete="off" />
                </div>
                <div class="flex align-items-center gap-5 mb-3">
                    <label for="email" class="font-semibold w-6rem">Email</label>
                    <InputText id="email" v-model="editOnboardee.email" class="flex-auto" autocomplete="off" />
                </div>
                <div class="flex align-items-center gap-5 mb-3">
                    <label for="phone" class="font-semibold w-6rem">Phone</label>
                    <InputText id="phone" v-model="editOnboardee.phoneNumber" class="flex-auto" autocomplete="off" />
                </div>
                <div class="flex align-items-center gap-5 mb-3">
                    <label for="gender" class="font-semibold w-6rem">Gender</label>
                    <select id="gender" v-model="editOnboardee.gender" class="flex-auto">
                        <option value="female">Female</option>
                        <option value="male">Male</option>
                        <option value="other">Other</option>
                    </select>
                </div>
                <div class="flex align-items-center gap-3 mb-3">
                    <label for="nationality" class="font-semibold w-6rem">Nationality</label>
                    <select id="nationality" v-model="editOnboardee.nationality" class="flex-auto">
                        <option v-for="country in countries" :value="country.name" :key="country.name">{{ country.name }}</option>
                    </select>
                </div>
                <div class="flex align-items-center gap-5 mb-3">
                    <label for="annualSalary" class="font-semibold w-6rem">Annual Salary</label>
                    <InputText id="annualSalary" v-model="editOnboardee.annualSalary" class="flex-auto" autocomplete="off" />
                </div>
                <div class="flex align-items-center gap-5 mb-3">
                    <label for="payrollNumber" class="font-semibold w-6rem">Payroll Number</label>
                    <InputText id="payrollNumber" v-model="editOnboardee.payrollNumber" class="flex-auto" autocomplete="off" />
                </div>
                <div class="flex align-items-center gap-5 mb-3">
                    <label for="startDate" class="font-semibold w-6rem">Start Date</label>
                    <InputText id="startDate" type="date" class="flex-auto" v-model="editOnboardee.startDate" />
                </div>
                <div class="flex align-items-center gap-5 mb-3">
                    <label for="state" class="font-semibold w-6rem">State</label>
                    <select id="state" v-model="editOnboardee.state" class="flex-auto">
                        <option value="INCOMING">INCOMING</option>
                        <option value="ONGOING">ONGOING</option>
                        <option value="COMPLETED">COMPLETED</option>
                        <option value="ABORTED">ABORTED</option>
                    </select>
                </div>
                <div class="flex align-items-center gap-5 mb-3">
                    <label for="process" class="font-semibold w-6rem">Process:</label>
                    <select id="process" v-model="editOnboardee.processId" class="flex-auto">
                        <option v-for="proc in processes" :value="proc.id" :key="proc.id">{{ proc.title }}</option>
                    </select>
                </div>
            </div>
            <div v-else>
                <p>Onboardee not found</p>
            </div>
            <div class="flex justify-content-end gap-2">
                <Button type="button" label="Cancel" severity="secondary" @click="showEditDialog = false"></Button>
                <Button type="button" label="Save" @click="saveChanges"></Button>
            </div>
        </Dialog>
    </main>
</template>

<script>
import axios from 'axios';
import Button from 'primevue/button';
import Dialog from 'primevue/dialog';
import InputText from 'primevue/inputtext';
import Stepper from 'primevue/stepper';
import StepperPanel from 'primevue/stepperpanel';
import { useAuthStore } from '@/stores/auth';

export default {
    components: {
        Button,
        Dialog,
        InputText
    },
    data() {
        return {
            onboardee: null,
            process: null,
            showEditDialog: false,
            editOnboardee: null,
            countries: [],
            processes: [],
            editInfo: false,
            processFinished: false
        };
    },
    setup() {
        const authStore = useAuthStore();
        return { authStore };
    },
    mounted() {
        const onboardeeId = this.$route.params.id;
        this.fetchOnboardeeById(onboardeeId);
        this.fetchCountries();
        this.fetchProcesses();
        this.checkUserRoles();
    },
    methods: {
        async fetchOnboardeeById(onboardeeId) {
            try {
                const response = await axios.get(`${import.meta.env.VITE_API_URL}/onboardees/${onboardeeId}`, {headers: this.authStore.authData()});
                this.onboardee = response.data.onboardee;
                this.onboardee.startDate = new Date(response.data.onboardee.startDate);
                this.process = response.data.process;
                if (response.data.process) this.processFinished = this.checkProcessFinished()
                this.editOnboardee = { ...this.onboardee };
            } catch (error) {
                console.error('Error fetching onboardee:', error);
            }
        },
        async checkUserRoles() {
            const authStore = useAuthStore();
            // Check if the user has the necessary roles to edit
            this.canEdit = !authStore.user.roles.includes('EMPLOYEE');
        },
        async fetchCountries() {
            try {
                const response = await axios.get('https://api.openaq.org/v1/countries');
                this.countries = response.data.results;
            } catch (error) {
                console.error('Error fetching countries:', error);
            }
        },
        async fetchProcesses() {
            try {
                const response = await axios.get(`${import.meta.env.VITE_API_URL}/processes`, { headers: this.authStore.authData() })
                this.processes = response.data;
            } catch (error) {
                console.error('Error fetching countries:', error);
            }
        },
        async saveChanges() {
            try {
                const response = await axios.patch(`${import.meta.env.VITE_API_URL}/onboardees/${this.onboardee.id}`, this.editOnboardee, {headers: this.authStore.authData()});
                this.onboardee = response.data;
                this.editOnboardee = {...response.data};
                this.showEditDialog = false;
                this.fetchOnboardeeById(this.onboardee.id);

            } catch (error) {
                console.error('Error saving changes:', error);
            }
        },
        sendEmailToOnboardee() {
            window.location.href = `mailto:${this.onboardee.email}`;
        },
        async sendEmailToEmployee(userId) {
            try {
                const response = await axios.get(`${import.meta.env.VITE_API_URL}/users/${userId}`, {headers: this.authStore.authData()});
                window.location.href = `mailto:${response.data.email}`;
            } catch (error) {
                console.error('Error fetching user:', error);
            }
        },
        getDeadlineDate(date, deadline) {
            var currDate = new Date(date)
            currDate.setDate(date.getDate() - deadline)
            return currDate
        },
        calculateDaysLeft(date) {
            const oneDay = 24 * 60 * 60 * 1000;
            const firstDate = date;
            const secondDate = new Date();
            
            if (secondDate >= firstDate) return 0
            else return Math.round(Math.abs((secondDate - firstDate) / oneDay)) + 1;
        },
        async changeStepStatus(stepId, status) {
            this.process.steps.forEach(async (step) => {
                if (step.stepInfoId == stepId) {
                    try {
                        const request = {
                            id: step.stepInfoId,
                            status: status,
                            description: step.stepInfodDescription,
                            stepInProcessId: step.stepInProcessId,
                            onboardeeId: this.onboardee.id
                        }
                        await axios.patch(`${import.meta.env.VITE_API_URL}/steps-info/${step.stepInfoId}`, request, {headers: this.authStore.authData()});
                        step.stepInfoStatus = status
                    }  catch (error) {
                        console.error('Error updating step info:', error);
                    }
                }
            })
        },
        displayDependencies(step) {
            const allStepInProcessIds = step.dependencies.map(dep => {
                const currStep = this.process.steps.filter(step => step.stepInProcessId == dep)[0]
                return currStep.stepInfoId
            })
            return allStepInProcessIds;
        },
        checkProcessFinished() {
            return this.process.steps.filter(step => step.stepInfoStatus == "COMPLETED").length == this.process.steps.length
        },
        async postStepInfo(step) {
            this.editInfo = true
            try {
                const request = {
                    id: step.stepInfoId,
                    status: step.stepInfoStatus,
                    description: step.stepInfodDescription,
                    stepInProcessId: step.stepInProcessId,
                    onboardeeId: this.onboardee.id
                }
                await axios.patch(`${import.meta.env.VITE_API_URL}/steps-info/${request.id}`, request, {headers: this.authStore.authData()}).then(res => {
                    this.editInfo = false
                });
                this.checkProcessFinished()
            } catch (error) {
                console.error("Error updating step info", error)
            }
        }
    }
};
</script>

<style>
.p-stepper-title {
    display: flex;
    font-size: medium;
    flex-direction: column;
}
.p-stepper-action {
    background: none;
}
.p-stepper-toggleable-content {
    background: none;
}
</style>

<style scoped>
.step-title-owners-row {
    display: flex;
    flex-direction: row;
}
.step-title-row {
    display: flex;
    flex-direction: column;
    /* justify-content: space-between; */
    color: black;
}
.step-row {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}
.step-title-owners {
    display: flex;
    flex-direction: column;
    align-items: start;
    color: black;
}
.step-title-desc {
    padding-right: 1em;
    font-size: 1.5em;
    font-weight: normal;
    color: black;
    margin-bottom: 1em;
}
.process-content {
    display: flex;
    height: 100%;
    flex-direction: column;
    justify-content: space-between;
    /* align-items: center; */
    padding: 2rem;
    font: medium;
    background-color: #F9F9F9;
}
.step-content {
    overflow-y: auto;
    height: 40vh;
}
.stepper {
    width: 80%;
    margin-bottom: 1rem;
}
.not-found {
    font-size: large;
    color: grey;
}
.info-main {
    display: flex;
    flex-direction: row;
    width: 100%;
}
.info-process {
    display: flex;
    flex-direction: column;
    height: min-content;
    width: 100%;
    justify-content: center;
    justify-items: center;
    align-items: center;
    align-content: center;
    padding: 1em;
}
.info-process-not-found {
    display: flex;
    height: min-content;
    width: 100%;
    height: calc(100vh - 3.55rem - 10rem - 0.3rem);
    justify-content: center;
    justify-items: center;
    align-items: center;
    align-content: center;
}
.email-link {
    color: #033A65;
}
.email-link:hover {
    text-decoration: underline;
}
.company-color {
    background-color: #033A65;
    border-color: #033A65;
}
.onboardee-main {
    display: flex;
    flex-direction: row;
    width: 100%;
    align-items: center;
    background-color: #033A65;
    height: 9rem;
}
.onboardee-name {
    color: white;
    display: flex;
    flex-direction: column;
    height: 40%;
    width: 100%;
    margin-left: 5.25rem;
    justify-content: space-between;
}
.onboardee-primary {
    font-size: 1.75em;
    font-weight: bolder;
}
.parent-onboardee {
    width: max-content;
    margin-top: 0.1em;
    height: calc(100vh - 3.55rem - 10rem - 0.3rem);
}
.parent {
    display: flex;
    margin-top: 0.3em;
    height: calc(100vh - 3.55rem);
}

.info-onboardee{
    background-color: #F9F9F9;

}
.PersonalInformation {
    padding: 0 1em;
    overflow-y: auto;
}

.PersonalInformation h2 {
    font-size: 1.2em;
    padding-left: 2em;
    padding-right: 2em;
    font-weight: bold;
    margin-top: 1em;
    margin-bottom: 0.5em;
    text-align: start;
}

.process-name {
    font-size: 1.2em;
    width: 100%;
    padding-left: 2em;
    padding-right: 2em;
    font-weight: bold;
    margin-top: 1em;
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
    margin: 0.4em 2em;
    border: none;
}

select,
input[type="date"] {
    width: 100%;
    padding: 0.5rem;
    font-size: 0.8rem;
    border-radius: 5px;
    border: 1px solid #ccc;
}

.p-inputtext {
    padding: 0.5rem 0.5rem;
}

.flex-auto {
    width: 100%;
}

</style>
