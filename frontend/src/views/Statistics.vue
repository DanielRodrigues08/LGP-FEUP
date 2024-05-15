<template>
    <div>
      <Chart :type="'pie'" :data="chartData" :options="chartOptions" />
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import Chart from 'primevue/chart';
  
  export default {
    components: {
      Chart
    },
    data() {
      return {
        onboardees: [],
        chartData: null,
        chartOptions: {
          title: {
            display: true,
            text: 'Nationalities of Onboardees'
          },
          legend: {
            position: 'bottom'
          }
        }
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
          this.generateChartData();
        } catch (error) {
          console.error('Error fetching onboardees:', error);
        }
      },
      generateChartData() {
        const nationalityCounts = {};
        this.onboardees.forEach(onboardee => {
          if (nationalityCounts[onboardee.nationality]) {
            nationalityCounts[onboardee.nationality]++;
          } else {
            nationalityCounts[onboardee.nationality] = 1;
          }
        });
  
        this.chartData = {
          labels: Object.keys(nationalityCounts),
          datasets: [
            {
              data: Object.values(nationalityCounts),
              backgroundColor: [
                '#FF6384',
                '#36A2EB',
                '#FFCE56',
                '#4BC0C0',
                '#9966FF'
                // Add more colors if needed
              ],
              hoverBackgroundColor: [
                '#FF6384',
                '#36A2EB',
                '#FFCE56',
                '#4BC0C0',
                '#9966FF'
                // Add more colors if needed
              ]
            }
          ]
        };
      }
    }
  };
  </script>
  
  <style scoped>
  /* Add any custom styles here */
  </style>
  