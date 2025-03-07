document.addEventListener('DOMContentLoaded', function () {


    // Retrieve loan amount and tenure from sessionStorage
    const loanAmount = sessionStorage.getItem('loanAmountInSession');
    const loanTenure = sessionStorage.getItem('loanDurationInSession');

    // Check if values exist
    if (loanAmount && loanTenure) {
        console.log(`Loan Amount: AUD ${loanAmount}`);
        console.log(`Loan Tenure: ${loanTenure} months`);
        // You can also display these values on the page if needed
        document.getElementById('temp-loan-use-info').textContent =
            `Loan Amount: AUD ${loanAmount}, Loan Tenure: ${loanTenure} months`;
    } else {
        console.error('No loan data found in sessionStorage.');
    }


    // Get the user info 
    document.getElementById("person-basic-info").addEventListener("click", function (event) {
        event.preventDefault(); // Prevent form submission for validation

        // Title
        const title = document.querySelector('input[name="title"]:checked').value;


        const firstName = document.getElementById("first-name-input").value;

        // Middle name
        const middleName = document.getElementById("middle-name-input").value;

        // Last name
        const lastName = document.getElementById("last-name-input").value;
        if (lastName === "") {
            alert("Please specify your last name.");
            return;
        }

        // Date of birth
        const dob = document.getElementById("dob-input").value;
        const currentDate = new Date();
        const birthDate = new Date(dob);
        const age = currentDate.getFullYear() - birthDate.getFullYear();
        if (dob === "") {
            alert("Please specify your date of birth.");
            return;
        }
        if (age < 18) {
            alert("You must be 18 or older.");
            return;
        }

        // Address
        const address = document.getElementById("location-input").value;

        // Apt/Suite (Optional)
        const apt = document.querySelector('input[placeholder="Apt, Suite, etc (optional)"]').value;

        // City
        const city = document.getElementById("locality-input").value;

        // State/Province
        const state = document.getElementById("administrative_area_level_1-input").value;

        // Zip/Postal code
        const postalCode = document.getElementById("postal_code-input").value;

        // Country
        const country = document.getElementById("country-input").value;

        // Mobile number
        const mobileNumber = document.getElementById("phone-num-input").value;

        if (!validatePhoneNumber(mobileNumber)) {
            return;
        }

        // Email
        const email = document.getElementById("email-input").value;
        if (email === "") {
            alert("Please specify your email.");
            return;
        }

        // Terms & Marketing consent
        const termsChecked = document.getElementById("terms") ? document.getElementById("terms").checked : true; // Optional check, if you're using it.


        if (termsChecked) {
            // Collect form data into an object for further use or validation
            const borrowerData = {
                title,
                firstName,
                middleName,
                lastName,
                dob,
                address: address, // Set "N/A" if address is empty
                apt: apt || "N/A", // Set "N/A" if apt is empty
                city: city || "N/A", // Set "N/A" if city is empty
                state: state || "N/A", // Set "N/A" if state is empty
                postalCode: postalCode || "N/A", // Set "N/A" if postalCode is empty
                country: country || "N/A", // Set "N/A" if country is empty
                mobileNumber,
                email

            };


            function sendBorrowerData(borrowerData) {
                fetch('http://localhost:8080/api/borrowers/create', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(borrowerData)
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`HTTP error! Status: ${response.status}  +  ${response.body}`);
                        }
                        return response.json(); // Parse JSON response
                    })
                    .then(data => {
                        console.log("Server response:", data);

                        // Extract tokenbBounce from response
                        if (data.tokenbBounce) {
                            console.log("Received tokenbBounce: is ", data.tokenbBounce);

                            sessionStorage.setItem('tokenBounce', data.tokenbBounce);


                            console.log("Received basiqId:", data.basiqUserId);


                            sessionStorage.setItem('basiqUserId', data.basiqUserId);
                            sessionStorage.setItem('titleSession', borrowerData.title);
                            sessionStorage.setItem('firstNameSession', borrowerData.firstName);
                            sessionStorage.setItem('middleNameSession', borrowerData.middleName);
                            sessionStorage.setItem('lastNameSession', borrowerData.lastName);
                            sessionStorage.setItem('dobSession', borrowerData.dob);
                            sessionStorage.setItem('addressSession', borrowerData.address);
                            sessionStorage.setItem('aptSession', borrowerData.apt || "N/A");
                            sessionStorage.setItem('citySession', borrowerData.city || "N/A");
                            sessionStorage.setItem('stateSession', borrowerData.state || "N/A");
                            sessionStorage.setItem('postalCodeSession', borrowerData.postalCode || "N/A");
                            sessionStorage.setItem('countrySession', borrowerData.country || "N/A");
                            sessionStorage.setItem('mobileNumberSession', borrowerData.mobileNumber);

                            document.location.href = "bank-input.html";


                        } else {
                            console.warn("tokenbBounce not found in response.");
                        }
                    })
                    .catch(error => {
                        if (error.name === 'TypeError') {
                            // Handle network errors (e.g., no internet or server down)
                            console.error("Network error or CORS issue:", error.message);
                        } else if (error.message.includes('400')) {
                            // Handle Bad Request errors specifically
                            console.error("Client-side error (400): User not found.");
                        } else if (error.message.includes('500')) {
                            // Handle Internal Server errors
                            console.error("Server-side error (500): Something broke on the server.");
                        } else {
                            // Handle other errors
                            console.error("Error sending borrower data:", error.message);
                        }

                    });
            }

            sendBorrowerData(borrowerData);


            // You can then proceed to send this data to a server or further validation
        } else {
            alert("You must accept the terms and conditions to proceed.");
        }
    });

    function validatePhoneNumber(phoneNumber) {
        // Define the regex pattern for validation
        const validPattern = /^04\d{8}$/;


        if (phoneNumber === "") {
            alert("Please enter a valid phone number");
            return false;
        } else if (validPattern.test(phoneNumber)) {
            return true; // Valid number
        } else {
            alert(`${phoneNumber} is invalid plesae make sure it start with 04 and have 10 digits`);
            return false;
        }
    }


});
