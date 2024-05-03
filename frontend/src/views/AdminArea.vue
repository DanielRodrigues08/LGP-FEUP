<template>
    <div class="users">
      <div class="title-container">
        <h1>Admin area</h1>
        <router-link to="/add-user">
            <Button class="add-button" label="New User" icon="pi pi-plus" />
        </router-link>
      </div>
      
      <div class="search-container">
        <div>
          <InputText v-model="globalFilter.value" placeholder="Search for name" @input="filterUsers" />
        </div>
      </div>

      <DataTable class="custom-table" :value="users" :globalFilter="globalFilter" @filter="filterUsers">
        <Column field="id" header="ID"></Column>
        <Column field="name" header="Name"></Column>
        <Column field="email" header="Email"></Column>
        <Column field="role" header="Role"></Column>
      </DataTable>

    </div>
  </template>
  
  <script>
  import DataTable from 'primevue/datatable';
  import Column from 'primevue/column'; 
  import axios from 'axios';
  import Button from 'primevue/button';
  import InputText from 'primevue/inputtext';
  
  export default {
    components: {
      DataTable,
      Column,
      Button,
      InputText
    },
    data() {
      return {
        users: [],
        originalUsers: [],
        globalFilter: { value: null, matchMode: 'contains', field: 'name' },
        deleteConfirmationVisible: false,
        userToDelete: null,
        selectedUser: null
      };
    },
    mounted() {
      this.fetchUsers();
    },
    methods: {
      async fetchUsers() {
        try {
          const response = await axios.get('http://localhost:8081/users');
          this.users = response.data;
          this.originalUsers = response.data;
        } catch (error) {
          console.error('Error fetching users:', error);
        }
      },
      filterUsers() {
        this.globalFilter = {
          value: this.globalFilter.value,
          matchMode: 'contains',
          field: 'name'
        };
        if (this.globalFilter.value === '') {
          this.users = this.originalUsers;
        } else {
          this.users = this.originalUsers.filter(item => item.name.toLowerCase().includes(this.globalFilter.value.toLowerCase()));
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
  