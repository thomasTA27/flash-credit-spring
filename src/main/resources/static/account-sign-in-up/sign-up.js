



async function hashPassword(password, salt) {
    const encoder = new TextEncoder();
    const data = encoder.encode(password + salt); // Combine password and salt
    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
    return Array.from(new Uint8Array(hashBuffer))
        .map(b => b.toString(16).padStart(2, "0"))
        .join("");
}

async function generateSalt() {
    const randomBytes = new Uint8Array(16); // 16 bytes = 128 bits
    crypto.getRandomValues(randomBytes);
    return Array.from(randomBytes)
        .map(b => b.toString(16).padStart(2, "0"))
        .join("");
}

async function signupUser() {
    const tel = document.getElementById("tel").value;
    const password = document.getElementById("password").value;

    const salt = await generateSalt(); // Generate a random salt
    const hashedPassword = await hashPassword(password, salt); // Hash with salt

    const response = await fetch("http://localhost:8080/users/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ tel, password: hashedPassword, salt }), // Send hashed password and salt
    });

    const result = await response.json();

    if (result.status === "success") {
        alert("User created successfully!");
        window.location.href = "sign-in.html"; // Redirect after success
    } else {
        alert("Error: " + result.message);
    }
}

const form = document.getElementById("formId"); // Replace "formId" with the actual ID of your form
form.addEventListener('click', function (event) {

    console.log("we do somehting");
    event.preventDefault();
    signupUser();
});