// document.addEventListener('DOMContentLoaded', function () {
//     const token = sessionStorage.getItem("token"); // Retrieve token
//
//     if (!token) {
//         alert("You are not logged in. Redirecting to login page...");
//         window.location.href = "sign-in.html"; // Redirect to login page
//         return;
//     }
//
//     function checkToken() {
//         const currentToken = sessionStorage.getItem("token");
//         if (!currentToken) {
//             alert("Your session has expired. Redirecting to login page...");
//             window.location.href = "sign-in.html"; // Redirect to login page
//         }
//     }
//     const tokenCheckInterval = setInterval(checkToken, 500);
//
//     window.addEventListener('beforeunload', function () {
//         clearInterval(tokenCheckInterval);
//     });
//
//     // Fetch personal data from the server
//
//     const but =  document.getElementById("userInfo")
//
//     but.addEventListener('click' , function (event) {
//         event.preventDefault();
//         alert('you gay');
//     fetch("http://localhost:8080/flash-credit/UserServlet/getPersonalData", {
//         method: "GET",
//         headers: {
//             "Content-Type": "application/json",
//             "Authorization": "Bearer " + token, // Send token in the header
//         },
//     })
//     .then(response => {
//         if (!response.ok) {
//             throw new Error("Failed to fetch personal data");
//         }
//         return response.json();
//     })
//     .then(data => {
//         // Display personal data on the page
//         document.getElementById("userData").innerText = JSON.stringify(data, null, 2);
//     })
//     .catch(error => {
//         console.error("Error fetching personal data:", error);
//         alert("Error fetching personal data. Redirecting to login page...");
//         window.location.href = "sign-in.html"; // Redirect to login page
//     });
//
// } );
//
//
// const remove = this.getElementById("removeP");
//
// remove.addEventListener('click' , function (event) {
//     event.preventDefault();
//     alert('u remove');
//     sessionStorage.removeItem("token");
// } );
//
// });



document.addEventListener('DOMContentLoaded', function () {


    const token = sessionStorage.getItem("token"); // Retrieve token

    if (!token) {
        alert("You are not logged in. Redirecting to login page...");
        window.location.href = "sign-in.html"; // Redirect to login page
        return;
    }

    // Function to decode JWT token
    function decodeToken(token) {
        try {
            const base64Url = token.split('.')[1]; // Get the payload part of the token
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const payload = JSON.parse(atob(base64)); // Decode base64 and parse JSON
            return payload;
        } catch (error) {
            console.error("Error decoding token:", error);
            return null;
        }
    }

    // Function to check if the token is expired
    function isTokenExpired(token) {
        const payload = decodeToken(token);
        if (!payload || !payload.exp) {
            return true; // Assume expired if payload or exp is missing
        }
        const currentTime = Math.floor(Date.now() / 1000); // Current time in seconds
        return currentTime >= payload.exp; // Check if current time is greater than expiration time
    }

    // Function to check token validity
    function checkToken() {
        const currentToken = sessionStorage.getItem("token");
        if (!currentToken || isTokenExpired(currentToken)) {
            alert("Your session has expired. Redirecting to login page...");
            window.location.href = "sign-in.html"; // Redirect to login page
        }
    }

    const tokenCheckInterval = setInterval(checkToken, 1); // Check token every 500ms

    window.addEventListener('beforeunload', function () {
        clearInterval(tokenCheckInterval); // Clear interval when the page is unloaded
    });

    // Fetch personal data from the server
    const but = document.getElementById("userInfo");

    but.addEventListener('click', function (event) {
        event.preventDefault();
        fetch("http://localhost:8080/flash-credit/UserServlet/getPersonalData", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token, // Send token in the header
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch personal data");
                }
                return response.json();
            })
            .then(data => {
                document.getElementById("userData").innerText = JSON.stringify(data, null, 2);
            })
            .catch(error => {
                console.error("Error fetching personal data:", error);
                alert("Error fetching personal data. Redirecting to login page...");
                window.location.href = "sign-in.html"; // Redirect to login page
            });
    });

    const remove = document.getElementById("removeP");

    remove.addEventListener('click', function (event) {
        event.preventDefault();
        sessionStorage.removeItem("token");
        alert("Token removed. You are now logged out.");
    });
});
