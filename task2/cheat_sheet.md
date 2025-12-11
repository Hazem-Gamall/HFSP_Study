# Cheat Sheet: CrySL

#### **1. File Structure**
A CrySL file specifies the valid usage of a Java class using the following sections:

| Section | Description | Example |
| :--- | :--- | :--- |
| **SPEC** | The fully qualified name of the Java class being specified. | `SPEC javax.crypto.Cipher` |
| **OBJECTS** | Declares variables that match Java types. Used in events and predicates. | `java.lang.String transformation;` |
| **EVENTS** | Maps abstract labels to concrete method signatures. | `Init: init(key);` |
| **ORDER** | Defines the strict call order (Regular Expression). | `Init, (Update*, DoFinal)+` |

#### **2. Logic Sections (Rules)**
Logic is not attached directly to definitions. Instead, it is organized into specific **Sections** at the bottom of the file. Each section lists rules mapping an **Event** to a **Predicate** or defining alternatives.

| Section | Description |
| :--- | :--- | 
| **REQUIRES** | **Pre-conditions**. Defines which predicates on which variables are required to be satisfied in order for this specification to be valid (secure). |
| **ENSURES** | **Post-conditions**. Defines which predicates are ensured by this specification. The `after` clause is optional; if present, it ties the state to a specific event. | 
| **NEGATES** | **Revocations**. Defines which predicates are invalidated/removed *after* a specific event occurs. |
| **FORBIDDEN** | **Restrictions**. Lists methods that are **always forbidden** and suggests an alternative method to use instead. |
| **CONSTRAINTS** | Defines logical rules on variables (e.g., allowed algorithms, key sizes). |

* **Example Usage:**
  ```text
  REQUIRES
      generatedKey[key];

  ENSURES
      generatedKey[key] after GenerateKey;
      encrypted[output];

  NEGATES
      generatedKey[key] after Clear;

  FORBIDDEN
      oldInit => newInit;

#### **3. Common Constructs (Used in Constraints)**

These helpers are used to write logic in the **CONSTRAINTS** section:

* **`alg(string)`**: Extracts the algorithm name from a transformation string (e.g., returns "AES" from "AES/CBC/PKCS5Padding").

* **`mode(string)`**: Extracts the mode from a transformation string (e.g., "CBC").

* **`pad(string)`**: Extracts the padding scheme from a transformation string (e.g., "PKCS5Padding").

* **`noCallTo[Method]`**: A typestate constraint that forbids calling the method defined by `Method` (e.g., forbidding an `Init` method that lacks an IV).

* **`callTo[Method]`**:  A typestate constraint that checks if the specified `Method` has been called.

* **`length[var]`**: Constraints the length of a variable (e.g., string length or array length).

* **`instanceOf[var, Type]`**: Checks if a variable is of a specific Java type.

* **`in {...}`**: Set membership check. Returns true if the value is inside the set.

* *Example:* `mode in {"CBC", "GCM"}`

* **`=>` (Implication)**: Logical "If... Then..." operator.

* *Example:* `A => B` means "If A is true, then B **must** also be true."

* **Operators**: Standard math/logic operators are supported: `==`, `!=`, `<`, `>`, `<=`, `>=`, `&&` (AND), `||` (OR).

#### **4. Order Syntax (Sequence & Logic)**
Defines valid call order using Regular Expression syntax.

| Symbol | Name | Meaning | Example |
| :--- | :--- | :--- | :--- |
| **,** | **Sequence** | Strict succession. | `Init, Update` |
| **\|** | **Choice** | Choose exactly one. | `CBC \| GCM` |
| **( )** | **Grouping** | Group operations. | `(Init, Update)` |
| **\*** | **Zero+** | 0 or more times. | `Update*` |
| **+** | **One+** | 1 or more times. | `Init+` |
| **?** | **Optional** | 0 or 1 time. | `Info?` |

#### **5. Multi-Object Protocol**
The multi-object protocol allows specifications to depend on predicates from other specifications. This is done using the `REQUIRES` section in the requiring specification. and the `ENSURES` section in the providing specification.

**Example:**
- Specification A (KeyGenerator) ensures `generatedKey[key]`.
- Specification B (Cipher) requires `generatedKey[key]` to initialize the cipher with a valid key.

**Example:**
- Specification A (FileReader) ensures `opened[this]` after `Constructor`.
- Specification B (FileProcessor) requires `opened[fileReader]` to read data from the FileReader object.
- Additionally, Specification A can negate `opened[this]` after a `Close` method to indicate the file is no longer accessible.