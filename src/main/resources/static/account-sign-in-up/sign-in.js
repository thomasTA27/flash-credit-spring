
document.addEventListener('DOMContentLoaded', function() {
async function loginUser() {
    const tel = document.getElementById("tel").value;
    const password = document.getElementById("password").value;

    // Fetch the salt for the user
    const saltResponse = await fetch("http://localhost:8080/users/getSalt", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ tel }),
    });

    const saltResult = await saltResponse.json();
    const salt = saltResult.salt; // Retrieve the salt

    console.log("this is our salt " + salt);

    const hashedPassword = await hashPassword(password, salt); // Hash with the retrieved salt

    console.log("this is the hashpa " + hashedPassword);

    const response = await fetch("http://localhost:8080/users/signIn", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ tel, password: hashedPassword }), // Send hashed password
    });

    const result = await response.json();

    if (result.token) {
        sessionStorage.setItem("token", result.token); // Store JWT token
        // alert("Login successful!");
        window.location.href = "../index.html"; // Redirect to protected page
    } else {
        alert("Login failed: " + result.message);
    }
}
async function hashPassword(password, salt) {
    const encoder = new TextEncoder();
    const data = encoder.encode(password + salt); // Combine password and salt
    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
    return Array.from(new Uint8Array(hashBuffer))
        .map(b => b.toString(16).padStart(2, "0"))
        .join("");
}
const form = document.querySelector("form"); // Select the actual <form>
    
form.addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission
    
    console.log("Sign-in button clicked!"); // This should log correctly now


    loginUser(); // Call the login function
});

});