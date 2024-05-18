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
                    <Button class="custom-button" icon="pi pi-pencil" rounded aria-label="Filter" @click="showEditDialog = true" />
                </div>
                <div class="emptyColumn"> </div>
            </div>
            <div v-else>
                <h1>Onboardee not found</h1>
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

    export default {
        components: {
            Button,
            Dialog,
            InputText
        },
        data() {
            return {
                onboardee: null,
                showEditDialog: false,
                editOnboardee: null,
                countries: []
            };
        },
        mounted() {
            const onboardeeId = this.$route.params.id;
            this.fetchOnboardeeById(onboardeeId);
            this.fetchCountries();
        },
        methods: {
            async fetchOnboardeeById(onboardeeId) {
                try {
                    const response = await axios.get(`${import.meta.env.VITE_API_URL}/onboardees/${onboardeeId}`);
                    this.onboardee = response.data;
                    console.log(this.onboardee)
                    this.editOnboardee = { ...response.data };
                } catch (error) {
                    console.error('Error fetching onboardee:', error);
                }
            },
            async fetchCountries() {
                try {
                    const response = await axios.get('https://api.openaq.org/v1/countries');
                    this.countries = response.data.results;
                } catch (error) {
                    console.error('Error fetching countries:', error);
                }
            },
            async saveChanges() {
                try {
                    console.log(this.editOnboardee.value)
                    let data = JSON.stringify(this.editOnboardee);
                    const updateData = { ...this.editOnboardee };

                    data = {
                        id: this.editOnboardee.id,
                        name: this.editOnboardee.name,
                        email: this.editOnboardee.email,
                        gender: this.editOnboardee.gender,
                        annualSalary: this.editOnboardee.annualSalary,
                        nationality: this.editOnboardee.nationality,
                        phoneNumber: this.editOnboardee.phoneNumber,
                        payrollNumber: this.editOnboardee.payrollNumber,
                        startDate: this.editOnboardee.startDate,
                        state: this.editOnboardee.state,
                        stepsInfo: this.editOnboardee.stepsInfo,
                        activeProcess: this.editOnboardee.activeProcess

                    };
                    console.log(data)

                    await axios.put(
                        `${import.meta.env.VITE_API_URL}/onboardees/${this.onboardee.id}`,
                        data,
                        {
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        }
                    );

                    this.onboardee = { ...this.editOnboardee };
                    this.showEditDialog = false;
                } catch (error) {
                    console.error('Error saving changes:', error);
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
        color: #033A65;
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
