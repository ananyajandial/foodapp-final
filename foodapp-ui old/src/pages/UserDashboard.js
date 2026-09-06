import { useState } from 'react';

function UserDashboard({ apiRequest, onLogout , showValidationError}) {

  const defaultProfile = { userId: '', name: '', email: '', phonenumber: '', address: '' }
  const defaultOrder = { orderId: '', foodId: '', quantity: '' }
  const defaultSearch = { id: '', category: '', maxPrice: '', minPrice: '' }
  const [profile, setProfile] = useState(defaultProfile)
  const [order, setOrder] = useState(defaultOrder);
  const [search, setSearch] = useState(defaultSearch);


  let username = localStorage.getItem("loginUserName");

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="text-primary">Welcome {username}</h1>
        <button className="btn btn-danger" onClick={onLogout}>Logout</button>
      </div>

      <div className="mb-4 d-flex flex-wrap gap-2">
        <button className="btn btn-success" onClick={() => apiRequest('GET', '/admin/available')}>View Available Foods</button>
      </div>

      <div className="row">
        {/* Find Food */}
        <div className="col-12 mb-4">
          <div className="card shadow p-4">
            <h3 className="text-primary mb-3">Search Menu</h3>
            <div className="row">
              <div className="col-md-4">
                <div className="input-group mb-2">
                  <input className="form-control" placeholder="Category" value={search.category} onChange={e => setSearch({...search, category: e.target.value})} />
                  <button className="btn btn-outline-primary" onClick={async() => {
                          if (!search.category?.trim()) {
                            showValidationError("Please enter a category");
                            return;
                          }
                          await apiRequest('GET', `/admin/category/${search.category}`);
                          setSearch(defaultSearch);}}>Search Category</button>
                </div>
              </div>
              <div className="col-md-4">
                <div className="input-group mb-2">
                  <input className="form-control" placeholder="Max Price" type="number" value={search.maxPrice} onChange={e => setSearch({...search, maxPrice: e.target.value})} />
                  <button className="btn btn-outline-primary" onClick={async() => {
                    if (!search.maxPrice?.trim()) {
                            showValidationError("Please enter the price below which you want to search");
                            return;
                          }
                    await apiRequest('GET', `/admin/belowprice/${search.maxPrice}`);
                    setSearch(defaultSearch);
                    }}>Below Price</button>
                </div>
              </div>
              <div className="col-md-4">
                <div className="input-group mb-2">
                  <input className="form-control" placeholder="Min" type="number" value={search.minPrice} onChange={e => setSearch({...search, minPrice: e.target.value})} />
                  <input className="form-control" placeholder="Max" type="number" value={search.maxPrice} onChange={e => setSearch({...search, maxPrice: e.target.value})} />
                  <button className="btn btn-outline-primary" onClick={async() => {
                    if (!search.minPrice || !search.maxPrice?.trim()) {
                            showValidationError("Please enter the minimum and maximum price");
                            return;
                          }                    
                    await apiRequest('GET', `/admin/between/${search.minPrice}/${search.maxPrice}`);
                    setSearch(defaultSearch);
                  }}>Between</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Profile Management */}
       {/* Profile Management */}
<div className="col-md-6 mb-4">
  <div className="card shadow p-4 h-100">
    <h3 className="text-primary mb-3">My Profile</h3>

    <input
      className="form-control mb-2"
      placeholder="User ID"
      value={profile.userId}
      onChange={e => setProfile({ ...profile, userId: e.target.value })}
    />

    <input
      className="form-control mb-2"
      placeholder="Name"
      value={profile.name}
      onChange={e => setProfile({ ...profile, name: e.target.value })}
    />

    <input
      className="form-control mb-2"
      placeholder="Email"
      value={profile.email}
      onChange={e => setProfile({ ...profile, email: e.target.value })}
    />

    <input
      className="form-control mb-2"
      placeholder="Phone"
      value={profile.phonenumber}
      onChange={e => setProfile({ ...profile, phonenumber: e.target.value })}
    />

    <input
      className="form-control mb-3"
      placeholder="Address"
      value={profile.address}
      onChange={e => setProfile({ ...profile, address: e.target.value })}
    />

    <div className="d-flex flex-wrap gap-2">

      <button
        className="btn btn-primary"
        onClick={async () => {
          const { userId, ...newProfile } = profile;
          await apiRequest('POST', '/users/register', newProfile);
          setProfile(defaultProfile);
        }}
      >
        Register
      </button>

      <button
        className="btn btn-secondary"
        onClick={async () => {
          if (!profile.userId.trim()) {
            showValidationError("Please enter a valid userID to update user data");
            return;
          }

          await apiRequest(
            'PUT',
            `/users/update/${profile.userId}`,
            profile
          );

          setProfile(defaultProfile);
        }}
      >
        Update
      </button>

      <button
        className="btn btn-info text-white"
        onClick={async () => {
          if (!profile.userId?.trim()) {
            showValidationError("Please enter a valid UserID to view profile");
            return;
          }

          await apiRequest(
            'GET',
            `/users/userId/${profile.userId}`
          );

          setProfile(defaultProfile);
        }}
      >
        View Profile
      </button>

    </div>
  </div>
</div>

</div>
</div>
);
}

export default UserDashboard;