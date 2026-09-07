import { useState } from "react";
import FoodSearchCard from "../components/FoodSearchCard";

function UserDashboard({
    apiRequest,
    onLogout,
    showValidationError
}) {

    const defaultProfile = {
        userId: "",
        name: "",
        email: "",
        phonenumber: "",
        address: ""
    };

    const [profile, setProfile] =
        useState(defaultProfile);

    const username =
        localStorage.getItem("loginUserName");

    return (
        <div>

            <div className="d-flex justify-content-between align-items-center mb-4">

                <h1 className="text-primary">
                    Welcome {username}
                </h1>

                <button
                    className="btn btn-danger"
                    onClick={onLogout}
                >
                    Logout
                </button>

            </div>

            <div className="mb-4 d-flex flex-wrap gap-2">

                <button
                    className="btn btn-success"
                    onClick={() =>
                        apiRequest(
                            "GET",
                            "/admin/available"
                        )
                    }
                >
                    View Available Foods
                </button>

                <button
                    className="btn btn-primary"
                    onClick={() => {

                        const userId =
                            localStorage.getItem(
                                "currentUserId"
                            );

                        if (!userId) {

                            showValidationError(
                                "Please place an order first."
                            );

                            return;
                        }

                        apiRequest(
                            "GET",
                            `/orders/${userId}`
                        );

                    }}
                >
                    View My Orders
                </button>

            </div>

            <div className="row">

                {/* Profile Card */}

                <div className="col-md-6 mb-4">

                    <div className="card shadow p-4 h-100">

                        <h3 className="text-primary mb-3">
                            My Profile
                        </h3>

                        <input
                            className="form-control mb-2"
                            placeholder="User ID"
                            value={profile.userId}
                            onChange={(e) =>
                                setProfile({
                                    ...profile,
                                    userId: e.target.value
                                })
                            }
                        />

                        <input
                            className="form-control mb-2"
                            placeholder="Name"
                            value={profile.name}
                            onChange={(e) =>
                                setProfile({
                                    ...profile,
                                    name: e.target.value
                                })
                            }
                        />

                        <input
                            className="form-control mb-2"
                            placeholder="Email"
                            value={profile.email}
                            onChange={(e) =>
                                setProfile({
                                    ...profile,
                                    email: e.target.value
                                })
                            }
                        />

                        <input
                            className="form-control mb-2"
                            placeholder="Phone"
                            value={profile.phonenumber}
                            onChange={(e) =>
                                setProfile({
                                    ...profile,
                                    phonenumber: e.target.value
                                })
                            }
                        />

                        <input
                            className="form-control mb-3"
                            placeholder="Address"
                            value={profile.address}
                            onChange={(e) =>
                                setProfile({
                                    ...profile,
                                    address: e.target.value
                                })
                            }
                        />

                        <div className="d-flex flex-wrap gap-2">

                            <button
                                className="btn btn-primary"
                                onClick={async () => {

                                    const {
                                        userId,
                                        ...newProfile
                                    } = profile;

                                    await apiRequest(
                                        "POST",
                                        "/users/register",
                                        newProfile
                                    );

                                    setProfile(
                                        defaultProfile
                                    );
                                }}
                            >
                                Register
                            </button>

                            <button
                                className="btn btn-secondary"
                                onClick={async () => {

                                    if (
                                        !profile.userId.trim()
                                    ) {

                                        showValidationError(
                                            "Please enter a valid userID to update user data"
                                        );

                                        return;
                                    }

                                    await apiRequest(
                                        "PUT",
                                        `/users/update/${profile.userId}`,
                                        profile
                                    );

                                    setProfile(
                                        defaultProfile
                                    );
                                }}
                            >
                                Update
                            </button>

                            <button
                                className="btn btn-info text-white"
                                onClick={async () => {

                                    if (
                                        !profile.userId?.trim()
                                    ) {

                                        showValidationError(
                                            "Please enter a valid UserID to view profile"
                                        );

                                        return;
                                    }

                                    await apiRequest(
                                        "GET",
                                        `/users/userId/${profile.userId}`
                                    );

                                    setProfile(
                                        defaultProfile
                                    );
                                }}
                            >
                                View Profile
                            </button>

                        </div>

                    </div>

                </div>

                {/* Food Search Card */}

                <div className="col-md-6 mb-4">

                    <FoodSearchCard
                        apiRequest={apiRequest}
                        showValidationError={
                            showValidationError
                        }
                        isAdmin={false}
                    />

                </div>

            </div>

        </div>
    );
}

export default UserDashboard;