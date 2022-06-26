const auth = document.querySelector('#auth')

const urlAuth = 'http://localhost:8080/api/users/auth'
const urlRoles = 'http://localhost:8080/api/roles'
const urlUser = 'http://localhost:8080/api/users/'
const urlNewUser = 'http://localhost:8080/api/users'
const urlUserAuth = 'http://localhost:8080/api/users/auth'
const urlUsers = 'http://localhost:8080/api/users'

fetch(urlAuth)
    .then(res => res.json())
    .then(data => {
        // отображение информации о пользователи в шапке страницы
        auth.textContent = data.email
        auth.textContent += ' with roles: ';
        let firstIter = true
        data.authorities.forEach(role => {
            if (firstIter) {
                auth.textContent += role.roleName
                firstIter = false
            } else {
                auth.textContent += ', ' + role.roleName
            }
        })

    })

function getRoleForUser(user){
    let roles = ''
    user.authorities.forEach(role => {
        roles += role.roleName + ' '
    })
    return roles
}