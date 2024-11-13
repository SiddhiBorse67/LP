# import xmlrpc.client

# def main():
#     # Create an XML-RPC client
#     proxy = xmlrpc.client.ServerProxy("http://localhost:8000/RPC2")

#     # Call remote methods
#     result_add = proxy.add(5, 3)
#     result_subtract = proxy.subtract(10, 4)

#     print(f"Addition result: {result_add}")
#     print(f"Subtraction result: {result_subtract}")

# if __name__ == "__main__":
#     main()
    
    
    
    


import xmlrpc.client

def main():
    # Create an XML-RPC client
    proxy = xmlrpc.client.ServerProxy("http://localhost:8000/RPC2")
    
    # Take input from the user for addition
    a = int(input("Enter the first number for addition: "))
    b = int(input("Enter the second number for addition: "))
    
    # Take input from the user for subtraction
    x = int(input("Enter the first number for subtraction: "))
    y = int(input("Enter the second number for subtraction: "))
    
    # Call remote methods
    result_add = proxy.add(a, b)
    result_subtract = proxy.subtract(x, y)

    print(f"Addition result: {result_add}")
    print(f"Subtraction result: {result_subtract}")

if __name__ == "__main__":
    main()

