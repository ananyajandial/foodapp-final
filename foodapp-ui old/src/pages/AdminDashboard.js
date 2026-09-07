import { useState } from 'react';
import FoodSearchCard from '../components/FoodSearchCard';
function AdminDashboard({ apiRequest, onLogout , showValidationError}) {

  const defaultFood = { foodId: '', foodName: '', category: '', price: '', description: '', available: true }
  const defaultSearch = { id: '', category: '', maxPrice: '', minPrice: '' }

  const [food, setFood] = useState(defaultFood);
  const [search, setSearch] = useState(defaultSearch);

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="text-primary">Admin Dashboard</h1>
        <button className="btn btn-danger" onClick={onLogout}>Logout</button>
      </div>

      <div className="d-flex flex-wrap gap-2 mb-4">
        <button className="btn btn-success" onClick={() => apiRequest('GET', '/admin/all')}>View All Foods</button>
        <button className="btn btn-success" onClick={() => apiRequest('GET', '/admin/available')}>View Available Foods</button>
        <button className="btn btn-info text-white" onClick={() => apiRequest('GET', '/orders/allOrders')}>View All Orders</button>
        <button className="btn btn-warning text-white" onClick={() => apiRequest('GET', '/users/all')}>View All Users</button>
      </div>

      <div className="row">
        {/* Add / Update Food */}
        <div className="col-md-6 mb-4">
          <div className="card shadow p-4 h-100">
            <h3 className="text-primary mb-3">Add / Update Food</h3>
            <input className="form-control mb-2" placeholder="Food Name" value={food.foodName} onChange={e => setFood({...food, foodName: e.target.value})} />
            <select className="form-control mb-2" placeholder="Category" value={food.category} onChange={e => setFood({ ...food, category: e.target.value })} >
              <option value="">Select Category</option>
              <option value="FASTFOOD">FASTFOOD</option>
              <option value="CHINESE">CHINESE</option>
              <option value="SOUTHINDIAN">SOUTHINDIAN</option>
              <option value="NORTHINDIAN">NORTHINDIAN</option>
              <option value="DESSERT">DESSERT</option> 
            </select>

            <input className="form-control mb-2" placeholder="Price" type="number" value={food.price} onChange={e => setFood({...food, price: e.target.value})} />
            <input className="form-control mb-3" placeholder="Description" value={food.description} onChange={e => setFood({...food, description: e.target.value})} />
              
        <button className="btn btn-primary w-100" onClick={async () => {
          if (!food.foodName?.trim() || !food.category || !food.price || Number(food.price) <= 0 ) {
            showValidationError("Please fill all the required fields");
            return;
           }
            const { foodId, ...newFoodData } = food;
            await apiRequest('POST', '/admin/add', newFoodData);
            setFood(defaultFood);
          }}> Add Food </button>

          </div>
        </div>

        {/* Find / Delete Food */}
        <div className="col-md-6 mb-4">
    <FoodSearchCard
        apiRequest={apiRequest}
        showValidationError={showValidationError}
        isAdmin={true}
    />
</div>
        
  </div>
      </div>
  );
}
export default AdminDashboard;