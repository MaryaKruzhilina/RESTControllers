const userCrudHtml = 'http://localhost:8080/api/users'
console.log('Работа начата')
function fetchUserAuth(){
    return fetch('http://localhost:8080/api/currentUser')
        .then(response => {
            return response.json()
        })
        .then(user => {
            console.log('Data received: ', user)
            let infoEmailUserAuth = document.getElementById('emailUserAuth')
            let infoRolesUserAuth = document.getElementById('rolesUserAuth')
            infoRolesUserAuth.innerText = 'with roles: ' + user.roles.join(' ')
            infoEmailUserAuth.innerText = user.email
        })
        .catch(error => {
            console.error('Ошибка:', error);
        })
}
fetchUserAuth()