<template>
    <div class="users">
      <div class="title-container">
        <h1>Processes</h1>
        <router-link to="/"> <!--! CHANGE THIS LINK -->
        <Button class="add-button" label="New Process" icon="pi pi-plus" />
        </router-link>
      </div>

      <div class="search-container">
        <div>
          <InputText v-model="globalFilter.value" placeholder="Search for name" @input="filterProcesses" />
        </div>
      </div>

      <DataTable class="custom-table" removableSort :value="processes" paginator :rows="5" :rowsPerPageOptions="[5,10,20,50]" :globalFilter="globalFilter" @filter="filterUsers">
        <Column field="title" sortable header="Title" class="custom-header" :headerStyle="{ backgroundColor: '#033A65', color:'white', width:'20%' }" ></Column>
        <Column field="description" header="Description" class="custom-header" :headerStyle="{ backgroundColor: '#033A65', color:'white',width:'60%' }"></Column>
        <Column field="stepsCount"  class="custom-header " style="text-align: center;" :header="centeredHeader" :headerStyle="{ backgroundColor: '#033A65', color:'white',width:'20%'}">
          <template #header>
            <div class="center-text" style="margin: auto;">Number of steps</div>
          </template> 
          <template #body="slotProps">
            <div class="center-text">{{ slotProps.data.stepsCount }}</div>
          </template>
        </Column>
      </DataTable>

    </div>
  </template>
  
  <script>
  import DataTable from 'primevue/datatable';
  import Column from 'primevue/column'; 
  import axios from 'axios';
  import Button from 'primevue/button';
  import InputText from 'primevue/inputtext';
  import { authData } from "@/api/AuthProvider";

  export default {
    components: {
      DataTable,
      Column,
      Button,
      InputText
    },
    data() {
      return {
        processes: [],
        originalProcesses: [],
        globalFilter: { value: null, matchMode: 'contains', field: 'title' }
      };
    },
    mounted() {
      this.fetchProcesses();
    },
    methods: {
      async fetchProcesses() {
        try {
          const response = await axios.get(`${import.meta.env.VITE_API_URL}/processes`, {headers: authData()});
          this.processes = response.data.map(process =>({
            ...process,
            stepsCount: process.stepsInProcess.length,
          }));
          console.log(this.processes)
          this.originalProcesses = response.data.map(process =>({
            ...process,
            stepsCount: process.stepsInProcess.length,
          }));
          } catch (error) {
          console.error('Error fetching processes:', error);
        }
      },
      filterProcesses() {
        this.globalFilter = {
          value: this.globalFilter.value,
          matchMode: 'contains',
          field: 'title'
        };
        if (this.globalFilter.value === '') {
          this.processes = this.originalProcesses;
        } else {
          this.processes = this.originalProcesses.filter(item => item.title.toLowerCase().includes(this.globalFilter.value.toLowerCase()));
        }
      }
    }
  };
  </script>
  
  <style scoped>
    h1 {
      color: #033A65;
      font-weight:600;
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
    .search-container > div {
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





  </style>
  