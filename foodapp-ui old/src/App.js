import { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import LoginView from './pages/LoginPage';
import AdminDashboard from './pages/AdminDashboard';
import UserDashboard from './pages/UserDashboard';
import ResultView from './components/ResultView';
import FoodEditModal from './components/FoodEditModal';
import OrderModal from "./components/OrderModal";

import { loginUser, apiRequest as callApi } from "./services/ApiService";


function App() {
  const [page, setPage] = useState('login'); 
  const [role, setRole] = useState('');
  const [token, setToken] = useState(''); 

  
  //used to display when an exception or error occurs
  const [dataList, setDataList] = useState([]);
  const [viewTitle, setViewTitle] = useState('');

  const[error , setError] = useState(''); //??which error ??
  const[lastEndpoint , setLastEndpoint] = useState("");


const showValidationError = (message) => {
    setError(message);
    setDataList([]);
    setViewTitle("");
};

  const defaultFood = { foodId: '', foodName: '', category: '', price: '', description: '', available: true }
  const [editFood, setEditFood] = useState(defaultFood);
  const [showEditModal, setShowEditModal] = useState(false); 
  const handleEditFood = (food) => {
    setEditFood(food);
    setShowEditModal(true);
  };
  const handleDeleteFood = async (foodId) => {
    await apiRequest("DELETE", `/admin/delete/${foodId}`                        );
    }
    const handleStatusUpdate = async (
    orderId,
    status) => {
      await apiRequest( "PATCH", `/orders/update/status/${orderId}`,
      status); }

    const handleSaveFood = async () => {
    const { foodId, ...foodDto } = editFood;
    await apiRequest(
        "PATCH",
        `/admin/update/${foodId}`,
        foodDto
    );
    setShowEditModal(false);
    await apiRequest(
      "GET",
      lastEndpoint
    );
  }

  //order
    const [showOrderModal, setShowOrderModal] = useState(false);
    const [selectedFood, setSelectedFood] = useState(null);
    
    const defaultOrder = {
    userId: "",
    quantity: 1
};

const [order, setOrder] = useState(defaultOrder);

const executeOrderSubmission = async (payload) => {
    await apiRequest(
        "POST",
        "/orders/placeorder",
        payload
    );

    setShowOrderModal(false);
    setOrder(defaultOrder);
}

const handleLogin = async (username, password , setValidationError) => {   
  

    try {

        setError("");
        if (setValidationError) setValidationError("");
        const basicToken =  btoa(username + ":" + password);
        const response = await loginUser(
            username,
            password
        );
        console.log("user valid --->> "+username+" "+password);
        localStorage.setItem("loginUserName",username);
        localStorage.setItem("loginPassword",password);
        
        setRole(response.data.role);
        setToken(basicToken);

        setPage(
            response.data.role === "ROLE_ADMIN"
                ? "admin"
                : "user"
        );
      } catch (error) {
        
        console.log("Login error context Object : ",error);
        if (error.response) {
          if (error.response.status === 401 || error.response.status === 403) {
            setError("Invalid username or password.");
          } 
          else {
            setError(`Server returned an error: ${error.response.status}`);
          }
        }
    // 2. Check if the request was made but no response was received (Server is offline)
       else if (error.request || error.code === "ERR_NETWORK" || error.message.includes("Network Error")) {
        setError("Unable to connect to server.");
      }
    // 3. Fallback for any other internal client configuration error
    else {
      setError("Something went wrong. Please try again.");
    }
  }
};

const handleLogout = () => {
    setPage('login');
    setRole('');
    setToken('');
    setDataList([]);
    setViewTitle('');
    setError('');
};


  const apiRequest = async (method, endpoint, data = null) => {
    try {
      setError("");
if (method === "GET") {
    console.log("Setting last endpoint:", endpoint);
    setLastEndpoint(endpoint);
}
      const response = await callApi(
         method,
         endpoint,
         token,
         data
        );
        setDataList(response.data);
        setViewTitle("");
        // setViewTitle(`Result for: ${endpoint}`);
      } catch (error) {
        setDataList([]);
        setViewTitle("");
        if (!error.response) {
          setError("Unable to connect to server. Please make sure the backend is running.");
        } else {
          setError(error.response.data?.message || error.response.data || "Something went wrong" );
          }
        }
      };

  if (page === 'login') return <LoginView error = {error} setError = {setError} onLogin={handleLogin} />;

  return ( <div className="container mt-5 pb-5">
      {/* Dashboard View */}
      {role === 'ROLE_ADMIN' ? (
        <AdminDashboard apiRequest={apiRequest} onLogout={handleLogout} showValidationError = {showValidationError} />
      ) : (
        <UserDashboard apiRequest={apiRequest} onLogout={handleLogout} showValidationError = {showValidationError} />
      )}
<ResultView
    error={error}
    dataList={dataList}
    viewTitle={viewTitle}
    onEditFood={handleEditFood}
    onDeleteFood={handleDeleteFood}
    onStatusUpdate={handleStatusUpdate}
    role={role}
    showOrderModal={showOrderModal}
    setShowOrderModal={setShowOrderModal}
    selectedFood={selectedFood}
    setSelectedFood={setSelectedFood}
    onOrderSubmit={executeOrderSubmission}
/>
      <FoodEditModal show={showEditModal} food={editFood} setFood={setEditFood} onSave={handleSaveFood} onClose={() => setShowEditModal(false)} />
        <OrderModal
    show={showOrderModal}
    order={order}
    setOrder={setOrder}
    selectedFood={selectedFood}
    onClose={() => {
        setShowOrderModal(false);

        setOrder({
            userId: "",
            quantity: 1
        });
    }}
    onSubmit={() =>
        ({
            userId: Number(order.userId),
            foodId: selectedFood.foodId,
            quantity: Number(order.quantity)
        })
    }
/>

<OrderModal
    show={showOrderModal}
    order={order}
    setOrder={setOrder}
    selectedFood={selectedFood}
    onClose={() => setShowOrderModal(false)}
    onSubmit={() =>
        executeOrderSubmission({
            userId: Number(order.userId),
            foodId: selectedFood.foodId,
            quantity: Number(order.quantity)
        })
    }
/>
      </div>
      
    );
}

export default App;