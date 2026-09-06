import { useState } from 'react';

function LoginView({ error , setError , onLogin  }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [validationError, setValidationError] = useState("");

  const handleSubmit = () => {
    setValidationError("");
    setError("");

    if (!username.trim()) {
      setValidationError("Please Enter Username");
        return;
    }

    if (!password.trim()) {
        setValidationError("Please Enter Password");
        return;
    }

    onLogin(username, password , setValidationError);
};

  return (
    <div className="container mt-5">
      <div className="card shadow p-4 mx-auto" style={{maxWidth: '400px'}}>
        <h1 className="text-center text-primary mb-4">Food App Login</h1>
        <input className="form-control mb-3" placeholder="Username" value={username} 
        onChange={
          (e) => {setUsername(e.target.value);
          setValidationError("");
          setError("");
          }} />
        <input className="form-control mb-3" type="password" placeholder="Password" value={password} onChange={(e) => {
          setPassword(e.target.value);
          setValidationError("");
          setError("") ;}} />
          
          {validationError && (
            <div className="alert alert-warning">
                {validationError}
            </div>
        )}
 
        {error && (
            <div className="alert alert-danger">
                {error}
            </div>
        )}
      <button className="btn btn-primary w-100" onClick={handleSubmit}>
          Login</button>
      </div>
    </div>
  );
}

export default LoginView;