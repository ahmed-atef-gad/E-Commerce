import { useState } from "react";
import { Button, Alert } from "react-bootstrap";
import uploadProduct from "../../../api/Server";
import ErrorMessage from "../../../component/ErrorMessage";
import { motion } from "framer-motion";
import New from "../../../component/admin/new/New";
import ProductForm from "../../../component/admin/forms/ProductForm";

const AddProduct = () => {
  const [isPending, setIsPending] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [show, setShow] = useState(true);

  const handleSubmit = async (product, file) => {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("product", JSON.stringify(product));
    setIsPending(true);
    const { Pending, error } = await uploadProduct(formData);
    setError(error);
    if (!error) {
      setSuccess("Product added successfully");
      setShow(true);
      setTimeout(() => {
        setShow(false);
      }, 3000);
    } else {
      setTimeout(() => {
        setError(null);
      }, 3000);
    }
    setIsPending(Pending);
  };

  return (
    <New title="Add New Product">
      <div className="d-flex flex-column">
        <ProductForm handleSubmit={handleSubmit} isPending={isPending} />
        {error && <ErrorMessage> {error} </ErrorMessage>}
        {success && (
          <motion.div
            initial={{ opacity: 0, scale: 0.5 }}
            animate={{ opacity: 1, scale: 1 }}
            exit={{ opacity: 0, scale: 0.5 }}
            transition={{ duration: 0.5 }}
          >
            <Alert show={show} className="mt-3" variant="success">
              <Alert.Heading> {success} </Alert.Heading>
              <hr />
              <div className="d-flex justify-content-end">
                <Button
                  onClick={() => {
                    setShow(false);
                    setSuccess(null);
                  }}
                  variant="outline-success"
                >
                  Close me
                </Button>
              </div>
            </Alert>
          </motion.div>
        )}
      </div>
    </New>
  );
};

export default AddProduct;
