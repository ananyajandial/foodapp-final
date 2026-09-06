import { useState } from 'react';

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
            <input className="form-control mb-2" placeholder="Category" value={food.category} onChange={e => setFood({...food, category: e.target.value})} />
            <input className="form-control mb-2" placeholder="Price" type="number" value={food.price} onChange={e => setFood({...food, price: e.target.value})} />
            <input className="form-control mb-3" placeholder="Description" value={food.description} onChange={e => setFood({...food, description: e.target.value})} />
            
            <div className="d-flex gap-2">
              
        <button className="btn btn-primary w-100" onClick={async () => {
          if (!food.foodName?.trim() || !food.category?.trim() || !food.price || Number(food.price) <= 0 ) {
            showValidationError("Please fill all the required fields");
            return;
           }
            const { foodId, ...newFoodData } = food;
            await apiRequest('POST', '/admin/add', newFoodData);
            setFood(defaultFood);
          }}> Add New Food </button>

            </div>
          </div>
        </div>

        {/* Find / Delete Food */}
        <div className="col-md-6 mb-4">
          <div className="card shadow p-4 h-100">
            <h3 className="text-primary mb-3">Find & Delete Food</h3>
            <div className="input-group mb-2">
              <input className="form-control" placeholder="Food ID" value={search.id} onChange={e => setSearch({...search, id: e.target.value})} />
              <button className="btn btn-outline-primary" onClick={() => {
                if (!search.id?.trim()) {
                  showValidationError("Please enter Food ID");
                  return;
                }
                apiRequest('GET', `/admin/${search.id}`);
                setSearch(defaultSearch);
                }}>Get Food By ID</button>


            </div>

            <div className="input-group mb-2">
              <input className="form-control" placeholder="Category" value={search.category} onChange={e => setSearch({...search, category: e.target.value})} />
              <button className="btn btn-outline-primary" onClick={() => {
                if (!search.category?.trim()) {
                  showValidationError("Please enter Food ID");
                  return;
                }
                apiRequest('GET', `/admin/category/${search.category}`);
                setSearch(defaultSearch);}}>Get Category</button>
            </div>
            <div className="input-group mb-2">
              <input className="form-control" placeholder="Max Price" type="number" value={search.maxPrice} onChange={e => setSearch({...search, maxPrice: e.target.value})} />
              <button className="btn btn-outline-primary" onClick={() => {
                if (!search.maxPrice?.trim()) {
                  showValidationError("Please enter the Price");
                  return;
                }
                apiRequest('GET', `/admin/belowprice/${search.maxPrice}`);
                setSearch(defaultSearch);}}>Get Below Price</button>



    </div>
            </div>
          </div>
        </div>
      </div>
  );
}
export default AdminDashboard;