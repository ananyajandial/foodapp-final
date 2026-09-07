import FoodSearchCard from "../components/FoodSearchCard";

function UserDashboard({
    apiRequest,
    onLogout,
    showValidationError
}) {

    const username =
        localStorage.getItem("loginUserName");

    const getUserId = () => {

        const user =
            (username || "").toLowerCase();

        if (user === "ananya") return 100;
        if (user === "khushbu") return 101;
        if (user === "isheeta") return 102;

        return null;
    };

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

                        const userId = getUserId();

                        if (!userId) {

                            showValidationError(
                                "User ID not found"
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

                <button
                    className="btn btn-info text-white"
                    onClick={() => {

                        const userId = getUserId();

                        if (!userId) {

                            showValidationError(
                                "User ID not found"
                            );

                            return;
                        }

                        apiRequest(
                            "GET",
                            `/users/userId/${userId}`
                        );

                    }}
                >
                    View Profile
                </button>

            </div>

            <div className="row">

                <div className="col-12">

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