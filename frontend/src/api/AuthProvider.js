export const authData = () => {
    return {'Authorization': `Bearer ${localStorage.getItem('token')}`}
}