'use strict'
const apiURL = 'http://localhost:8082/'

let username = "Username Here";

function displayLoginDetails(data){
    /* Displays (if valid) the username, then either login or logout button*/
    console.log("Displaying Login Detail")
    let navbar = document.getElementById('rightNavbar');
    let logLink = document.getElementById('logNavLink');
    let finalItem = document.getElementById('finalNavListItem');

    let li = document.createElement('li');
    li.innerHTML = data;
    li.className = 'username';
    /* navbar.appendChild(li); */
    navbar.insertBefore(li,finalItem);

    logLink.innerText = "Logout";
}

displayLoginDetails(username);