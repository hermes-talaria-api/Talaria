import { Navigate } from "react-router-dom";

const PrivateRoute = ({selectRole, component : Component}) => {
    const login = true;
    const role = "admin"; 

    if(!login) {
        return <Navigate to="/" />
    }

    if(role !== selectRole) {
        alert("권한이 없는 계정 입니다.");
        return <Navigate to="/" />
    }

    return Component;
}

export default PrivateRoute;