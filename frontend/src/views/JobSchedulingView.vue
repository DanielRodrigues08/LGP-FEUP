<template>
  <div class="users">
    <div class="title-container">
      <h1>Job Scheduling</h1>
    </div>

    <div class="search-container">
      <div>
        <InputText v-model="globalFilter.value" placeholder="Search for Owner name" @input="filterStepsInfo" />
      </div>
    </div>

    <DataTable class="custom-table" removableSort :value="stepsInfo" paginator :rows="10"
      :rowsPerPageOptions="[5, 10, 20, 50]" :globalFilter="globalFilter" @filter="filterUsers">
      <Column field="processTitle" sortable header="Process" class="custom-header"
        :headerStyle="{ backgroundColor: '#033A65', color: 'white', width: '20%' }"></Column>
      <Column field="stepTitle" sortable header="Step" class="custom-header"
        :headerStyle="{ backgroundColor: '#033A65', color: 'white', width: '20%' }"></Column>
      <Column field="onboardeeName" header="Onboardee" class="custom-header"
        :headerStyle="{ backgroundColor: '#033A65', color: 'white', width: '20%' }">
        <template #body="slotProps">
          <a class="url-style" :href="generateOnboardeeUrl(slotProps.data.onboardeeId)">{{ slotProps.data.onboardeeName }}</a>
        </template></Column>
      <Column field="dueDate" sortable header="Due date" class="custom-header "
        :headerStyle="{ backgroundColor: '#033A65', color: 'white', width: '20%' }"></Column>
      <Column field="ownerName" header="Owner" class="custom-header "
        :headerStyle="{ backgroundColor: '#033A65', color: 'white', width: '20%' }"></Column>
      <Column field="backupName" header="Backup" class="custom-header "
        :headerStyle="{ backgroundColor: '#033A65', color: 'white', width: '20%' }"></Column>
    </DataTable>

  </div>
</template>

<script>
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import axios from 'axios';
import Button from 'primevue/button';
import InputText from 'primevue/inputtext';
import { useAuthStore } from '@/stores/auth';

export default {
  components: {
    DataTable,
    Column,
    Button,
    InputText
  },
  data() {
    return {
      stepsInfo: [],
      originalStepsInfo: [],
      globalFilter: { value: null, matchMode: 'contains', field: 'title' }
    };
  },
  setup() {
    const authStore = useAuthStore();
    return { authStore };
  },
  mounted() {
    this.fetchJobScheduling();
  },
  methods: {
    async fetchJobScheduling() {
      try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/job-scheduling`, { headers: this.authStore.authData() });
        this.stepsInfo = response.data;
        this.originalStepsInfo = response.data;
      } catch (error) {
        console.error('Error fetching processes');
      }
    },
    filterStepsInfo() {
      this.globalFilter = {
        value: this.globalFilter.value,
        matchMode: 'contains',
        field: 'ownerName'
      };
      if (this.globalFilter.value === '') {
        this.stepsInfo = this.originalStepsInfo;
      } else {
        this.stepsInfo = this.originalStepsInfo.filter(item => item.ownerName.toLowerCase().includes(this.globalFilter.value.toLowerCase()));
      }
    },    
    generateOnboardeeUrl(onboardeeId) {
      return `/onboardees/${encodeURIComponent(onboardeeId)}`;
    }
  }
};
</script>

<style scoped>
h1 {
  color: #033A65;
  font-weight: 600;
  margin-right: 2rem;
}

.add-button {
  background-color: #033A65;
  color: white;
  border-color: rgba(0, 0, 0, 0.1);
}

.title-container {
  display: flex;
  align-items: center;
  margin-top: 1.5rem;
  margin-left: 2rem;
}

.search-container {
  display: flex;
  margin-top: 1rem;
  margin-left: 2rem;
}

.search-container>div {
  margin-right: 1rem;
}

.custom-table {
  max-width: 90%;
  margin: 0 auto;
  margin-top: 1em;
  margin-bottom: 1em;

}

.center-text {
  text-align: center;
}

.url-style{
  color: black;
}

.url-style:hover{
  color: #003A65;
  font-size: 105%;
}
</style>