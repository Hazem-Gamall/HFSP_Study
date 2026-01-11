# Cheat Sheet: FUMSL

#### **1. File Structure**
A FUMSL file specifies the valid usage of a Java class using the following sections:

| Section | Description | Example |
| :--- | :--- | :--- |
| **TARGET** | The fully qualified name of the Java class being specified. | `TARGET javax.crypto.Cipher` |
| **VARS** | Declares variables that match Java types. These are used as method parameters, in constraints, and for guarantees. | `java.lang.String transformation` |
| **CONSTRAINTS** | Defines logical rules on variables or state (see *Common Constructs*). | `c1: alg(transformation) == "AES"` |
| **METHODS** | Lists the API's public methods mapped to internal labels. | `i1: init(key)` |
| **ACTIONS** | Logic attached to methods (Pre/Post-conditions). | `requires c1` |
| **FLOW** | Defines the strict call order (Regular Expression). | `Init, (Update*, DoFinal)+` |

#### **2. Actions (Method Logic)**
Actions are attached to method definitions or the flow. They enforce rules or update the object's state.

* **`requires constraint`**: **Pre-condition**. Checks internal variables or state logic.
    * *Meaning:* The method call is a **misuse** if this constraint evaluates to false.
    * *Example:* `requires c1` (Enforces that the algorithm variable is "AES").
* **`requires guarantee`**: **Dependency Check**. A pre-condition for object dependencies (Multi-Object Protocol).
    * *Meaning:* The method call is a **misuse** unless the specific variable holds the required guarantee from another specification.
    * *Example:* `requires javax.crypto.KeyGenerator.generatedKey[key]` (Requires `key` was created by a KeyGenerator).
* **`ensures guarantee`**: **Post-condition**. Updates state after success.
    * *Meaning:* After this method executes successfully, the specified guarantee is applied to the variable.
    * *Example:* `ensures generatedKey[key]` (Marks the key as valid/generated).
* **`revokes guarantee`**: **Revocation**. Invalidates a state.
    * *Meaning:* After this method executes, the specified guarantee is deemed unsatisfied on the variable.
    * *Example:* `revokes encrypted[output]` (Marks the output as no longer considered valid/encrypted).

#### **3. Common Constructs (Used in Constraints)**

These helpers are used to write logic in the **CONSTRAINTS** section:

* **`alg(string)`**: Extracts the algorithm name from a transformation string (e.g., returns "AES" from "AES/CBC/PKCS5Padding").

* **`mode(string)`**: Extracts the mode from a transformation string (e.g., "CBC").

* **`pad(string)`**: Extracts the padding scheme from a transformation string (e.g., "PKCS5Padding").

* **`noCallTo(Method)`**: A typestate constraint that forbids calling the method defined by `Method` (e.g., forbidding an `Init` method that lacks an IV).

* **`callTo(Method)`**:  A typestate constraint that checks if the specified `Method` has been called.

* **`length(var)`**: Constraints the length of a variable (e.g., string length or array length).

* **`instanceOf(var, Type)`**: Checks if a variable is of a specific Java type.

* **`in {...}`**: Set membership check. Returns true if the value is inside the set.

* *Example:* `mode in {"CBC", "GCM"}`

* **`=>` (Implication)**: Logical "If... Then..." operator.

* *Example:* `A => B` means "If A is true, then B **must** also be true."

* **Operators**: Standard math/logic operators are supported: `==`, `!=`, `<`, `>`, `<=`, `>=`, `&&` (AND), `||` (OR).

#### **4. Flow Syntax (Sequence & Logic)**
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
The multi-object protocol allows specifications to depend on guarantees from other specifications. This is done using the `requires guarantee` action from the requiring specification. and the `ensures guarantee` action from the providing specification.

**Example:**
- Specification A (KeyGenerator) ensures `generatedKey[key]`.
- Specification B (Cipher) requires `generatedKey[key]` to initialize the cipher with a valid key.

**Example:**
- Specification A (FileReader) ensures `opened[this]` after `Constructor`.
- Specification B (FileProcessor) requires `opened[fileReader]` to read data from the FileReader object.
- Additionally, Specification A can revoke `opened[this]` after a `Close` method to indicate the file is no longer accessible.