const inputRolesNew = document.querySelector('#inputRoles-new')

function addRolesForSelect(iR){
    let optionRole
    iR.options.length = 0
    fetch(urlRoles)
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                optionRole = document.createElement('option')
                optionRole.text = role.roleName
                optionRole.value = role.id
                iR.appendChild(optionRole)
            })
        })
}

function addRolesForSelectById(iR, id){
    let optionRole
    iR.options.length = 0
    fetch(urlUser+id)
        .then(res => res.json())
        .then(user => {
            user.roleSet.forEach(role => {
                optionRole = document.createElement('option')
                optionRole.text = role.roleName
                optionRole.value = role.id
                iR.appendChild(optionRole)
            })
        })
}

function getRoleForUserById(id , node){
    let res = ''
    fetch(urlUser+id)
        .then(res => res.json())
        .then(user => {
            user.roleSet.forEach(role => {
                res += role.roleName + ' '
            })
            node.textContent = res
        })
}

addRolesForSelect(inputRolesNew)