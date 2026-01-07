import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AdminDashboard from "./AdminDashboard";
import ManagerDashboard from "./ManagerDashboard";
import MemberDashboard from "./MemberDashboard";

const Dashboard = () => {
  const navigate = useNavigate();

  let user = null;
  try {
    user = JSON.parse(localStorage.getItem("user"));
  } catch {
    user = null;
  }
s
  useEffect(() => {
    if (!user) navigate("/login");
  }, [user, navigate]);

  if (!user) return null;

  const role = (user.role || user.userType || "member").toLowerCase();

  if (role.includes("admin")) return <AdminDashboard />;
  if (role.includes("manager")) return <ManagerDashboard />;
  return <MemberDashboard />;
};

export default Dashboard;
