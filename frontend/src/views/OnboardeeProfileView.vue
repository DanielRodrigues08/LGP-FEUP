<template>
    <main>
        <div class="parent">
            <div class="div1">
              <img src="https://www.w3schools.com/w3images/lights.jpg" alt="Lights">
              <div class="OnboardName">
                <h2>Sofia Martins</h2>
                <!---<h2>{{ onboardee.name }}</h2>-->
                <p>Onboardee</p>
              </div>
              
            </div>
            <div class="PersonalInformation">  
                <h2>Personal Information</h2>
                <p>sofiamartins@gmail.com
                    <span>
                        <!---{{ onboardee.email }}-->
                    </span>
                </p>
                <p>987654321
                    <span>
                        <!---{{ onboardee.phone_number }}-->
                    </span>
                </p>

            </div>
            <div class="div3">  </div>
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
            fetchOnboardeeById(onboardeeId) {
            // Make an API call to retrieve user information based on user ID
            axios.get(`/onboardees/${onboardeeId}`)
                .then(response => {
                    this.onboardeeId = response.data;
                })
                .catch(error => {
                    console.error('Error fetching user:', error);
                });
    }
  }
    };
</script>

<style scoped>
.parent {
    display: grid;
    grid-template-columns: 1fr 4fr;
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
    width:14em;
    height:14em;
    box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.5);
}

.div1 .OnboardName {
    margin-left: 2em;
    color: white;
    align-self: center;
    font-size: 1.5em;
    padding:1em;

    
}

.OnboardName h2 {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 1em;
}

.PersonalInformation {
    grid-area: 2 / 1 / 6 / 2;
    background-color:rgb(226, 222, 217);
    /*background-color: #F9F9F9;*/
    height:100%;
}

.PersonalInformation h2 {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 1em;
    padding:1em;
    text-align: center;
}

.PersonalInformation p {
    font-size: 1em;
    margin-bottom: 1em;
    padding:1em;
    text-align: center;
}


.div3 {
    grid-area: 2 / 2 / 3 / 3;
    background-color: white;
}
</style>