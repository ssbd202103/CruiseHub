export default function LogoutService() {
    window.localStorage.removeItem('token')
}