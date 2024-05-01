<template>
  <div class="onboardees">
    <div class="title-container">
      <h1>Incoming onboardees</h1>
      <router-link to="/add-onboardee">
          <Button class="add-button" label="New onboardee" icon="pi pi-plus" />
      </router-link>
    </div>
    
    <div class="search-container">
      <div>
        <InputText v-model="globalFilter.value" placeholder="Search for name" @input="filterOnboardees" />
      </div>
    </div>
    <DataTable class="custom-table" :value="onboardees" :globalFilter="globalFilter" @filter="filterOnboardees">
      <Column field="id" header="ID"></Column>
      <Column field="name" header="Name"></Column>
      <Column field="email" header="Email"></Column>
      <Column field="phoneNumber" header="Phone"></Column>
      <Column header="">
        <template #body="slotProps">
          <Button class="delete-button" @click="showDeleteConfirmation(slotProps.data)">
            <i class="pi pi-trash" style="color: grey;"></i>
          </Button>
        </template>
      </Column>
    </DataTable>

    <Dialog v-model="deleteConfirmationVisible" header="Confirm" :visible="deleteConfirmationVisible" modal :closable="false">
      <p>Are you sure you want to delete "{{ selectedOnboardee.name }}"?</p>
      <div class="dialog-buttons">
        <Button label="No" class="p-button-secondary" @click="cancelDelete" outlined />
        <Button label="Yes" class="p-button-success" @click="deleteOnboardee" outlined />
      </div>
    </Dialog>
  </div>
</template>

<script>
import DataTable from 'primevue/datatable';
import Column from 'primevue/column'; 
import axios from 'axios';
import Button from 'primevue/button';
import Dialog from 'primevue/dialog';
import InputText from 'primevue/inputtext';

export default {
  components: {
    DataTable,
    Column,
    Button,
    Dialog,
    InputText
  },
  data() {
    return {
      onboardees: [],
      originalOnboardees: [],
      globalFilter: { value: null, matchMode: 'contains', field: 'name' },
      deleteConfirmationVisible: false,
      onboardeeToDelete: null,
      selectedOnboardee: null
    };
  },
  mounted() {
    this.fetchOnboardees();

  },
  methods: {
    async fetchOnboardees() {
      try {
        const response = await axios.get('http://localhost:8081/onboardees');
        this.onboardees = response.data;
        this.originalOnboardees = response.data;

      } catch (error) {
        console.error('Error fetching onboardees:', error);
      }
    },
    showDeleteConfirmation(onboardee) {
      this.selectedOnboardee = onboardee;
      this.deleteConfirmationVisible = true;
    },
    async deleteOnboardee() {
      try {
        await axios.delete(`http://localhost:8081/onboardees/${this.selectedOnboardee.id}`);
        // Remove the deleted onboardee from the list
        this.onboardees = this.onboardees.filter(item => item.id !== this.selectedOnboardee.id);
        this.deleteConfirmationVisible = false;
      } catch (error) {
        console.error('Error deleting onboardee:', error);
      }
    },
    cancelDelete() {
      this.deleteConfirmationVisible = false;
    },
    filterOnboardees() {
      this.globalFilter = {
        value: this.globalFilter.value,
        matchMode: 'contains',
        field: 'name'
      };
      if (this.globalFilter.value === '') {
    this.onboardees = this.originalOnboardees;
  } else {
    this.onboardees = this.originalOnboardees.filter(item => item.name.toLowerCase().includes(this.globalFilter.value.toLowerCase()));
  }    }
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
  }
  .delete-button {
    background-color: transparent;
    border: none; 
  }
  .dialog-buttons {
    display: flex;
    justify-content: flex-end;
    margin-top: 1rem;
  }

  .dialog-buttons > .p-button {
    margin-left: 0.5rem;
  }
</style>
